package com.ebitik.sync.service;

import java.time.OffsetDateTime;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.exception.ConstraintViolationException;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.db.entity.SalesforceLead;
import com.ebitik.sync.db.repository.AccessAccountRepository;
import com.ebitik.sync.db.repository.SalesforceLeadRepository;
import com.ebitik.sync.dto.SalesforceLeadDTO;
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
public class LeadSyncService implements SyncService {

	private final AccessAccountRepository repository;
	private final SalesforceLeadRepository sfLeadRepository;

	public void sync(Long accountId) {
		AccessAccount accessAccount = repository.findById(accountId).orElseThrow(() -> new SyncException("Access Account not found by id: "+accountId));

		ForceApi api = getApi(accessAccount.getUsername(), accessAccount.getPassword());

		OffsetDateTime lastRecordModifiedDate = Constants.MINIMUM_QUERY_DATE;

		SalesforceLead lastUpdatedRecord = sfLeadRepository.findFirstByAccessAccountOrderByLastModifiedDateDesc(accessAccount);
		if (lastUpdatedRecord != null) {
			lastRecordModifiedDate = lastUpdatedRecord.getLastModifiedDate();
		}

		boolean isDone = false;
		String nextRecordsUrl = null;

		while(!isDone) {
			QueryResult<SalesforceLeadDTO> result = fetch(accessAccount, api, lastRecordModifiedDate, "Lead",
					"id, Name, LastModifiedDate, Email", nextRecordsUrl, SalesforceLeadDTO.class);

			if (result.getTotalSize() == 0) {
				log.debug("no update found for lead sync!");
				return;
			}

			for (SalesforceLeadDTO dto : result.getRecords()) {
				SalesforceLead sa = new SalesforceLead();
				sa.setEmail(dto.getEmail());
				sa.setId(dto.getId());
				sa.setName(dto.getName());
				sa.setLastModifiedDate(dto.getLastModifiedDate());
				sa.setAccessAccount(accessAccount);

				try {
					sfLeadRepository.save(sa);
				} catch (ConstraintViolationException e) {
					log.warn("record is already inserted. Error: {}", e.getMessage());
				}
			}

			isDone = result.isDone();
			nextRecordsUrl = result.getNextRecordsUrl();
		}

	}

}
