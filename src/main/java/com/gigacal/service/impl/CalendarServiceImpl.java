package com.gigacal.service.impl;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.repository.CalendarRepository;
import com.gigacal.service.ICalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CalendarServiceImpl implements ICalendarService {

    private CalendarRepository calendarRepository;

    @Override
    public List<CalendarEntity> findAll() {
        return calendarRepository.findAll();
    }

    @Override
    public void createCalendar(CalendarEntity calendarEntity) {
        calendarEntity.setCreateDate(LocalDateTime.now());
        calendarRepository.save(calendarEntity);
    }
}
