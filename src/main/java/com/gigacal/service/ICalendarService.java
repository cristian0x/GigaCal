package com.gigacal.service;

import com.gigacal.dto.CalendarDTO;
import com.gigacal.entity.CalendarEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICalendarService {

    CalendarDTO createCalendar(CalendarDTO calendarDTO, Authentication authentication);

    void validateThatCalendarBelongsToUser(Long calendarId, Long userId);

    void validateThatCalendarBelongsToUser(String name, Long userId);

    CalendarEntity findCalendarById(Long calendarId, Authentication authentication);

    List<CalendarDTO> findCalendarsForUser(Authentication authentication);

    void deleteCalendar(Long calendarId, Authentication authentication);

    List<CalendarEntity> findCalendarsByName(String name, Authentication authentication);

    CalendarDTO updateCalendar(CalendarDTO calendar, Authentication authentication);

    List<CalendarEntity> findAllUserCalendars(Authentication authentication);
}
