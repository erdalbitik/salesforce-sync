package com.ebitik.sync.service;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class QuartzService {

	private final Scheduler quartz;

	public void schedule(Class <? extends Job> jobClass, Long syncAccountId, Integer interval) {
		String jobName = jobClass.getSimpleName()+"SyncJob"+syncAccountId;
		String jobTriggerName = jobClass.getSimpleName()+"SyncTrigger"+syncAccountId;
		String jobGroupName = jobClass.getSimpleName()+"SyncGroup";

		JobDetail job = JobBuilder.newJob(jobClass)
				.withIdentity(jobName, jobGroupName)
				.usingJobData("accessAccountId", Objects.toString(syncAccountId)).storeDurably(true)
				.build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobTriggerName, jobGroupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(interval)
						.withMisfireHandlingInstructionNextWithRemainingCount()
						.repeatForever())
				.build();

		try {
			quartz.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			log.error("Error: Job unschedule problem: {}", jobName);
		}

	}

	public void unschedule(Class <? extends Job> jobClass, Long syncAccountId) {
		String jobName = jobClass.getSimpleName()+"SyncJob"+syncAccountId;
		String jobGroupName = jobClass.getSimpleName()+"SyncGroup";

		try {
			quartz.deleteJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			log.error("Error: Job unschedule problem : {}", jobName);
		}
	}

}