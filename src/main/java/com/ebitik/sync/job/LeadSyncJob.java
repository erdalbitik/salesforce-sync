package com.ebitik.sync.job;

import com.ebitik.sync.service.LeadSyncService;
import com.ebitik.sync.service.SyncService;

public class LeadSyncJob extends SyncJob {

	@Override
	Class<? extends SyncService> getSyncServiceClass() {
		return LeadSyncService.class;
	}

}