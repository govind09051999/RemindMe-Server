package com.server.RemindMe.jobs;

import com.server.RemindMe.models.Reminder;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import static com.server.RemindMe.constants.Types.DESCRIPTION;
import static com.server.RemindMe.constants.Types.TITLE;

@Component
public class JobMapper {

    public JobDetail buildJobDetail(Reminder reminder, UUID id) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put(reminder.getPlatform(), reminder.getDetails());
        jobDataMap.put(TITLE, reminder.getTitle());
        jobDataMap.put(DESCRIPTION, reminder.getDescription());

        // TODO: replace EmailJob.class with generic way based on platform (use switch case)
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(id.toString(), "email-jobs") // TODO: UI should send UUID for guest user
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
