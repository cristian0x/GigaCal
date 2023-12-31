package com.gigacal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventDto(
    @Nonnull Long calendarId,
    @Nonnull String name,
    @Nullable String description,
    @Nonnull boolean isCyclic,
    @Nonnull LocalDate startDate,
    @Nullable LocalDate endDate,
    @Nonnull @JsonFormat(pattern="HH:mm:ss") LocalTime time,
    @Nonnull int duration
){}
