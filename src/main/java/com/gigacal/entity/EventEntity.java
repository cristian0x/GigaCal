package com.gigacal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "events")
@Data
public class EventEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID uuid;

    private Long calendarId;

    private String name;

    private String description;

    private boolean isShared;

    private boolean isCyclic;

    private String days;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime time;

    private LocalTime duration;

}
