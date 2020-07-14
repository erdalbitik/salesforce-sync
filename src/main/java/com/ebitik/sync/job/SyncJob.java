package com.ebitik.sync.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ebitik.sync.exception.SyncException;
import com.ebitik.sync.service.SyncService;

import io.quarkus.arc.Arc;
import io.quarkus.arc.ManagedContext;
import lombok.extern.slf4j.Slf4j;

@DisallowConcurrentExecution
@Slf4j
public abstract class SyncJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Class<? extends SyncService> syncServiceClass = getSyncServiceClass();
		log.info("Sync started for {}", syncServiceClass);

		Long accessAccountId = context.getJobDetail().getJobDataMap().getLong("accessAccountId");

		ManagedContext requestContext = Arc.container().requestContext();
		if (!requestContext.isActive()) {
			requestContext.activate();
		}

		try {
			Arc.container().instance(syncServiceClass).get().sync(accessAccountId);
		} catch (Exception e) {
			log.error("{} job got error: {}", syncServiceClass, e.getMessage());
			throw new SyncException(e);
		}

		log.info("Sync finished for {}", syncServiceClass);
	}

	abstract Class<? extends SyncService> getSyncServiceClass();

}
