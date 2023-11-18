package com.gigacal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "EVENTS")
@Data
public class EventEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
