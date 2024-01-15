package com.gigacal.service;

import com.gigacal.entity.CalendarEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICalendarService {

    void createCalendar(CalendarEntity calendarEntity, Authentication authentication);

    void validateThatCalendarBelongsToUser(Long calendarId, Long userId);

    void validateThatCalendarBelongsToUser(String name, Long userId);

    CalendarEntity findCalendarById(Long calendarId, Authentication authentication);

    List<CalendarEntity> findCalendarsByUserId(Long userId, Authentication authentication);

    void deleteCalendar(Long calendarId, Authentication authentication);

    List<CalendarEntity> findCalendarsByName(String name, Authentication authentication);

    void updateCalendar(Long calendarId, CalendarEntity calendar, Authentication authentication);
}
