package com.gigacal.service.impl;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.exception.CalendarException;
import com.gigacal.repository.CalendarRepository;
import com.gigacal.service.ICalendarService;
import com.gigacal.specifications.CalendarSpecification;
import com.gigacal.utils.ObjectUtil;
import com.gigacal.utils.StringUtil;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
    public CalendarEntity findCalendarById(Long calendarId) {
        return calendarRepository.findById(calendarId).orElseThrow(() ->
                new NoResultException("calendar with id " + calendarId + " not found"));
    }

    @Override
    public List<CalendarEntity> findCalendarsByUserId(Long userId) {
        return calendarRepository.findCalendarsByUserId(userId).orElseThrow(() ->
                new NoResultException("calendars for user id " + userId + " not found"));
    }

    @Override
    public List<CalendarEntity> getCalendarsByExactColumn(String column, String parameter) {
        switch (column) {
            case "name":
                return calendarRepository.findAll(Specification.where(CalendarSpecification.hasName(parameter)));
            default:
                throw new NoResultException("endpoint does not exists");
        }
    }

    @Override
    public void deleteCalendar(Long calendarId) {
        if (!calendarRepository.existsById(calendarId)) {
            throw new NoResultException("calendar with id " + calendarId + " not found");
        }
        calendarRepository.deleteById(calendarId);
    }

    @Override
    @Transactional
    public void updateCalendar(Long calendarId, CalendarEntity calendar) {
        CalendarEntity calendarToUpdate = calendarRepository.findById(calendarId).orElseThrow(() ->
                new NoResultException("calendar with id " + calendarId + " not found"));

        if (!StringUtil.isEmpty(calendar.getName())) {
            calendarToUpdate.setName(calendar.getName());
        }
        else {
            throw new CalendarException.IncorrectDataProvided();
        }
        if (!ObjectUtil.isEmpty(calendar.getUser())) {
            calendarToUpdate.setUser(calendar.getUser());
        }
        else {
            throw new CalendarException.IncorrectDataProvided();
        }
        calendarToUpdate.setUpdateDate(LocalDateTime.now());
    }
}
