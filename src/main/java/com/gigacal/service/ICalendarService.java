package com.gigacal.service;

import com.gigacal.entity.CalendarEntity;

import java.util.List;

public interface ICalendarService {

    List<CalendarEntity> findAll();

    void createCalendar(CalendarEntity calendarEntity);

    CalendarEntity findCalendarById(Long calendarId);

    List<CalendarEntity> findCalendarsByUserId(Long userId);
}
