package com.SpringMongo.QuartzConfig;

public class SchedulerConstants {
	public static final String SAMPLE_JOB_POLLING_TRIGGER_KEY = "samplePollerTrigger";
	public static final String SAMPLE_JOB_POLLING_GROUP = "samplePollerGroup";
	public static final String SAMPLE_JOB_POLLING_JOB_KEY = "samplePollingJob";
	public static final String QUARTZ_SCHEDULER_DB_NAME = "quartz.jobStore.dbName";
	
	public static final String LEAD_NOTIFICATION_POLLING_JOB_KEY = "leadNotificationJob";
	public static final String LEAD_NOTIFICATION_POLLING_GROUP = "leadNotificationGroup";
	public static final String LEAD_NOTIFICATION_POLLING_TRIGGER_KEY = "leadNotificationTrigger";
	
	public static final String SCHEDULED_EXAM_POLLING_JOB_KEY = "scheduledExamJob";
	public static final String SCHEDULED_EXAM_POLLING_GROUP = "scheduledExamGroup";
	public static final String SCHEDULED_EXAM_POLLING_TRIGGER_KEY = "scheduledExamTrigger";
	public static final String SCHEDULED_REPORT_POLLING_TRIGGER_KEY = "scheduledReportTrigger";
	public static final String SCHEDULED_REPORT_POLLING_GROUP = "scheduledReportGroup";
	
	public static final String ENVIRONMENT = "spring.profiles.active";
	public static final String MONGO_URI = "spring.data.mongodb.uri";
	public static final String MONGO_USERNAME = "db.username";
	public static final String MONGO_PASSWORD = "db.password";
	public static final String SERVER_NAME = "server.name";
}
