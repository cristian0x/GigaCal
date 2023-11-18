package com.gigacal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "events")
@Data
public class EventEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private Long id;

    private Long calendarId;

    private String name;

    private String description;

    private String link;

    private boolean isShared;

    private boolean isCyclic;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime time;

    private int duration;


}
