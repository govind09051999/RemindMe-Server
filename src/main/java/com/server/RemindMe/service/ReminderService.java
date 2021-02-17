package com.server.RemindMe.service;

import com.server.RemindMe.dao.ReminderRepository;
import com.server.RemindMe.jobs.JobMapper;
import com.server.RemindMe.models.Reminder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class ReminderService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private ReminderRepository reminderRepository;

    public ResponseEntity<String> addReminder(Reminder reminder) {
       try {
           ZonedDateTime dateTime = ZonedDateTime.of(reminder.getDate(),reminder.getTimeZone());
           if (dateTime.isBefore(ZonedDateTime.now())) {
               return ResponseEntity
                       .badRequest()
                       .body("date cannot be in the past");
           }
           Reminder savedReminder = reminderRepository.save(reminder);
           JobDetail jobDetail = jobMapper.buildJobDetail(reminder, savedReminder.getId());
           Trigger trigger = jobMapper.buildJobTrigger(jobDetail,dateTime);
           scheduler.scheduleJob(jobDetail, trigger);
           return ResponseEntity
                   .accepted()
                   .body("reminder added successfully");
       } catch (Exception e) {
           return ResponseEntity
                   .badRequest()
                   .body("Internal Server Error");
       }
    }

//    private JobDetail buildJobDetail(Reminder reminder) {
//        JobDataMap jobDataMap = new JobDataMap();
//
//        jobDataMap.put(reminder.getPlatform(), reminder.getDetails());
//        jobDataMap.put(TITLE, reminder.getTitle());
//        jobDataMap.put(DESCRIPTION, reminder.getDescription());
//
//        // TODO: replace EmailJob.class with generic way based on platform (use switch case)
//        return JobBuilder.newJob(EmailJob.class)
//                .withIdentity(UUID.randomUUID().toString(), "email-jobs") // TODO: UI should send UUID for guest user
//                .withDescription("Send Email Job")
//                .usingJobData(jobDataMap)
//                .storeDurably()
//                .build();
//    }
//
//    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
//        return TriggerBuilder.newTrigger()
//                .forJob(jobDetail)
//                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
//                .withDescription("Send Email Trigger")
//                .startAt(Date.from(startAt.toInstant()))
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
//                .build();
//    }

}
