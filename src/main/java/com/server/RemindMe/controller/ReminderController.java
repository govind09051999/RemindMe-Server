package com.server.RemindMe.controller;

import com.server.RemindMe.models.Reminder;
import com.server.RemindMe.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ReminderController {
    @Autowired
    private ReminderService reminderService;

    @PostMapping(path = "/add-reminder")
    public ResponseEntity<String> addReminder(@RequestBody Reminder reminder) {
        return reminderService.addReminder(reminder);
    }

}
