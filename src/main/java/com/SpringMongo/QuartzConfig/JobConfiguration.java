package com.SpringMongo.QuartzConfig;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.SpringMongo.CommonConstant.CommonConstants;
import com.SpringMongo.PropertyReader.PropertyReader;



@Configuration
public class JobConfiguration {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	@Qualifier(CommonConstants.QUERY_PROPERTY_READER)
	private PropertyReader propertyReader;
	
	private static String newCronExpression;

	@PostConstruct
	private void initialize() throws Exception {
		newCronExpression = propertyReader.getProperty(SchedulerConstants.JOB_EXPRESSION);
		schedulerFactoryBean.getScheduler().addJob(leadNotificationJobDetail(), true, true);

		if (!schedulerFactoryBean.getScheduler()
				.checkExists(new TriggerKey(SchedulerConstants.SIMPLE_POLLING_TRIGGER_KEY,
						SchedulerConstants.SIMPLE_POLLING_GROUP))) {
			schedulerFactoryBean.getScheduler().scheduleJob(leadNotificationJobDetail(), leadNotificationJobTrigger());
		}
	}
	
	private static JobDetail leadNotificationJobDetail() {
		JobDetailImpl simpleJobs = new JobDetailImpl();
		simpleJobs.setKey(new JobKey(SchedulerConstants.SIMPLE_POLLING_JOB_KEY,
				SchedulerConstants.SIMPLE_POLLING_GROUP));
		simpleJobs.setJobClass(SimpleScheduler.class);
		simpleJobs.setDurability(true);
		return simpleJobs;
	}
	private static Trigger leadNotificationJobTrigger() {
		CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(newCronExpression);
		CronTrigger crontrigger = TriggerBuilder.newTrigger()
				.withIdentity(SchedulerConstants.SIMPLE_POLLING_JOB_KEY,
						SchedulerConstants.SIMPLE_POLLING_GROUP)
				.withSchedule(builder.withMisfireHandlingInstructionFireAndProceed()).build();
		return crontrigger;
	}

}
