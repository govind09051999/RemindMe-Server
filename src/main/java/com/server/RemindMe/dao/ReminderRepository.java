package com.server.RemindMe.dao;

import com.server.RemindMe.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

}
