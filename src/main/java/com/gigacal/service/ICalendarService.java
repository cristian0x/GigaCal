package com.gigacal.service;

import com.gigacal.entity.CalendarEntity;

import java.util.List;

public interface ICalendarService {

    List<CalendarEntity> findAll();

    void createCalendar(CalendarEntity calendarEntity);

    CalendarEntity findCalendarById(Long calendarId);

    List<CalendarEntity> findCalendarsByUserId(Long userId);

    void deleteCalendar(Long calendarId);

    List<CalendarEntity> findCalendarsByName(String name);

    void updateCalendar(Long calendarId, CalendarEntity calendar);
}
