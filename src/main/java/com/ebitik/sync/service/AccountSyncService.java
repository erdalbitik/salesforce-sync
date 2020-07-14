package com.ebitik.sync.service;

import java.time.OffsetDateTime;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.exception.ConstraintViolationException;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.db.entity.SalesforceAccount;
import com.ebitik.sync.db.repository.AccessAccountRepository;
import com.ebitik.sync.db.repository.SalesforceAccountRepository;
import com.ebitik.sync.dto.SalesforceAccountDTO;
import com.ebitik.sync.exception.SyncException;
import com.ebitik.sync.util.Constants;
import com.force.api.ForceApi;
import com.force.api.QueryResult;

import io.quarkus.runtime.Startup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Startup
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class AccountSyncService implements SyncService {

	private final AccessAccountRepository repository;
	private final SalesforceAccountRepository sfAccountRepository;

	public void sync(Long accountId) {
		AccessAccount accessAcc = repository.findById(accountId).orElseThrow(() -> new SyncException("Access Account not found by id: "+accountId));

		ForceApi api = getApi(accessAcc.getUsername(), accessAcc.getPassword());

		OffsetDateTime lastRecordModifiedDate = Constants.MINIMUM_QUERY_DATE;

		SalesforceAccount lastUpdatedRecord = sfAccountRepository.findFirstByAccessAccountOrderByLastModifiedDateDesc(accessAcc);

		if (lastUpdatedRecord != null) {
			lastRecordModifiedDate = lastUpdatedRecord.getLastModifiedDate();
		}

		boolean isDone = false;
		String nextRecordsUrl = null;

		while(!isDone) {
			QueryResult<SalesforceAccountDTO> result = fetch(accessAcc, api, lastRecordModifiedDate, "Account",
					"id, Name, LastModifiedDate, AnnualRevenue", nextRecordsUrl, SalesforceAccountDTO.class);

			if (result.getTotalSize() == 0) {
				log.debug("no update found for account sync");
			}

			for (SalesforceAccountDTO dto : result.getRecords()) {
				SalesforceAccount sa = new SalesforceAccount();
				sa.setAnnualRevenue(dto.getAnnualRevenue());
				sa.setExternalId(dto.getExternalId());
				sa.setId(dto.getId());
				sa.setName(dto.getName());
				sa.setLastModifiedDate(dto.getLastModifiedDate());
				sa.setAccessAccount(accessAcc);

				try {
					sfAccountRepository.save(sa);
				} catch (ConstraintViolationException e) {
					log.warn("Record is already inserted. Error: {}", e.getMessage());
				}

				isDone = result.isDone();
				nextRecordsUrl = result.getNextRecordsUrl();
			}
		}

	}

}
