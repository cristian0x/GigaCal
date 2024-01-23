package com.gigacal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

public record EventDto(
        @Nullable Long id,
        @Nonnull Long calendarId,
        @Nonnull String title,
        @Nonnull Boolean isCyclic,
        @Nonnull Boolean allDay,
        @Nullable Instant start,
        @Nullable Instant end,
        @Nullable @JsonFormat(pattern="HH:mm:ss") LocalTime startTime,
        @Nullable @JsonFormat(pattern="HH:mm:ss") LocalTime endTime,
        @Nullable Instant startRecur,
        @Nullable Instant endRecur,
        @Nullable List<String> daysOfWeek,
        @Nullable String urlStr,
        @Nullable String description
){}
