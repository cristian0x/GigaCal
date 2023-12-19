package com.gigacal.service.impl;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.exception.CalendarException;
import com.gigacal.repository.CalendarRepository;
import com.gigacal.service.ICalendarService;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CalendarServiceImpl implements ICalendarService {

    private final CalendarRepository calendarRepository;

    @Override
    public List<CalendarEntity> findAll() {
        return calendarRepository.findAll();
    }

    @Override
    public void createCalendar(final CalendarEntity calendarEntity) {
        calendarEntity.setCreateDate(LocalDateTime.now());
        calendarRepository.save(calendarEntity);
    }

    @Override
    public CalendarEntity findCalendarById(final Long calendarId) {
        return calendarRepository.findById(calendarId).orElseThrow(() ->
                new NoResultException("calendar with id " + calendarId + " not found"));
    }

    @Override
    public List<CalendarEntity> findCalendarsByUserId(final Long userId) {
        return calendarRepository.findCalendarsByUserId(userId).orElseThrow(() ->
                new NoResultException("calendars for user id " + userId + " not found"));
    }

    @Override
    public List<CalendarEntity> findCalendarsByName(final String name) {
        return calendarRepository.findCalendarsByName(name).orElseThrow(() ->
                new NoResultException("calendars for name " + name + " not found"));
    }

    @Override
    public void deleteCalendar(final Long calendarId) {
        if (!calendarRepository.existsById(calendarId)) {
            throw new NoResultException("calendar with id " + calendarId + " not found");
        }
        calendarRepository.deleteById(calendarId);
    }

    @Override
    @Transactional
    public void updateCalendar(final Long calendarId, final CalendarEntity calendar) {
        CalendarEntity calendarToUpdate = calendarRepository.findById(calendarId).orElseThrow(() ->
                new NoResultException("calendar with id " + calendarId + " not found"));

        if (!calendar.getName().isEmpty()) {
            calendarToUpdate.setName(calendar.getName());
        }
        else {
            throw new CalendarException.IncorrectDataProvided();
        }
        if (!calendar.getUser().toString().isEmpty()) {
            calendarToUpdate.setUser(calendar.getUser());
        }
        else {
            throw new CalendarException.IncorrectDataProvided();
        }
        calendarToUpdate.setUpdateDate(LocalDateTime.now());
    }
}
