package com.gigacal.entity;

import com.gigacal.entity.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    private String title;

    private boolean isShared;

    private Boolean isCyclic;

    private boolean allDay;

    private Instant startStr;

    private Instant endStr;

    private LocalTime startTime;

    private LocalTime endTime;

    private Instant startRecur;

    private Instant endRecur;

    @Convert(converter = StringListConverter.class)
    private List<String> daysOfWeek = new ArrayList<>();

    private String urlStr;

    private String description;
}
