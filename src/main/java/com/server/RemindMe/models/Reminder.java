package com.server.RemindMe.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Reminder")
@Table(name = "Reminder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID id;
    private String username;
    @NotNull
    private String details;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private ZoneId timeZone;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String platform;
    private boolean isGuestUser;

}
