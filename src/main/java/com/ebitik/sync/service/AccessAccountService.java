package com.ebitik.sync.service;

import javax.enterprise.context.ApplicationScoped;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.db.repository.AccessAccountRepository;
import com.ebitik.sync.exception.SyncException;
import com.ebitik.sync.job.AccountSyncJob;
import com.ebitik.sync.job.ContactSyncJob;
import com.ebitik.sync.job.LeadSyncJob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class AccessAccountService {

	private final AccessAccountRepository repository;
	private final QuartzService quartzService;

	public AccessAccount addAccessAccount(String label, String username, String password, Integer syncPeriod) {
		log.info("AccessAccount add called! Username: {}", username);

		AccessAccount accessAcount = repository.findByUsername(username);
		if(accessAcount != null) {
			throw new SyncException("Access account already exists!");
		}

		accessAcount = new AccessAccount();
		accessAcount.setLabel(label);
		accessAcount.setPassword(password);
		accessAcount.setSyncPeriod(syncPeriod);
		accessAcount.setUsername(username);

		accessAcount = repository.save(accessAcount);

		quartzService.schedule(AccountSyncJob.class, accessAcount.getId(), syncPeriod);
		quartzService.schedule(ContactSyncJob.class, accessAcount.getId(), syncPeriod);
		quartzService.schedule(LeadSyncJob.class, accessAcount.getId(), syncPeriod);

		accessAcount.setScheduled(true);

		return repository.save(accessAcount);
	}

	public void deleteAccessAccount(String username) {
		log.info("AccessAccount delete called! Username: {}", username);

		AccessAccount accessAcount = repository.findByUsername(username);
		if(accessAcount == null) {
			throw new SyncException("Access account not found!");
		}

		quartzService.unschedule(AccountSyncJob.class, accessAcount.getId());
		quartzService.unschedule(ContactSyncJob.class, accessAcount.getId());
		quartzService.unschedule(LeadSyncJob.class, accessAcount.getId());

		repository.deleteById(accessAcount.getId());
	}

	public AccessAccount getAccessAccount(String username) {
		return repository.findByUsername(username);
	}
}
