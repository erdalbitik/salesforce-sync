package com.ebitik.sync.job;

import com.ebitik.sync.service.AccountSyncService;
import com.ebitik.sync.service.SyncService;

public class AccountSyncJob extends SyncJob {

	@Override
	Class<? extends SyncService> getSyncServiceClass() {
		return AccountSyncService.class;
	}

}
