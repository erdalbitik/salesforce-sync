package com.ebitik.sync.job;

import com.ebitik.sync.service.ContactSyncService;
import com.ebitik.sync.service.SyncService;

public class ContactSyncJob extends SyncJob {

	@Override
	Class<? extends SyncService> getSyncServiceClass() {
		return ContactSyncService.class;
	}

}
