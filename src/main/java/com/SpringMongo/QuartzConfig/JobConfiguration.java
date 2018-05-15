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
		newCronExpression = propertyReader.getProperty(CommonConstants.JOB_EXPRESSION);
		schedulerFactoryBean.getScheduler().addJob(leadNotificationJobDetail(), true, true);

		if (!schedulerFactoryBean.getScheduler()
				.checkExists(new TriggerKey(SchedulerConstants.LEAD_NOTIFICATION_POLLING_TRIGGER_KEY,
						SchedulerConstants.LEAD_NOTIFICATION_POLLING_GROUP))) {
			schedulerFactoryBean.getScheduler().scheduleJob(leadNotificationJobDetail(), leadNotificationJobTrigger());
		}
	}
	
	private static JobDetail leadNotificationJobDetail() {
		JobDetailImpl leadNotificationJobDetail = new JobDetailImpl();

		leadNotificationJobDetail.setKey(new JobKey(SchedulerConstants.LEAD_NOTIFICATION_POLLING_JOB_KEY,
				SchedulerConstants.LEAD_NOTIFICATION_POLLING_GROUP));
		leadNotificationJobDetail.setJobClass(LeadNotificationScheduler.class);
		leadNotificationJobDetail.setDurability(true);

		return leadNotificationJobDetail;
	}



	private static Trigger leadNotificationJobTrigger() {
		CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(newCronExpression);
		CronTrigger crontrigger = TriggerBuilder.newTrigger()
				.withIdentity(SchedulerConstants.SCHEDULED_EXAM_POLLING_TRIGGER_KEY,
						SchedulerConstants.SCHEDULED_EXAM_POLLING_GROUP)
				.withSchedule(builder.withMisfireHandlingInstructionFireAndProceed()).build();
		return crontrigger;
	}

}
