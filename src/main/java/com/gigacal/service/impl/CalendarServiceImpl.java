package com.gigacal.service.impl;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.exception.ForbiddenActionException;
import com.gigacal.repository.CalendarRepository;
import com.gigacal.service.ICalendarService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CalendarServiceImpl implements ICalendarService {

    private final Logger LOGGER = LoggerFactory.getLogger(CalendarServiceImpl.class);

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
    public void validateThatCalendarBelongsToUser(final Long calendarId, final Long userId) {
        final Optional<CalendarEntity> optionalCalendarEntity = this.calendarRepository.findByIdAndUserId(calendarId, userId);

        if (optionalCalendarEntity.isEmpty()) {
            LOGGER.warn("Calendar with id={} doesn't belong to user with id={}", calendarId, userId);
            throw new ForbiddenActionException("Calendar with id=" + calendarId + " doesn't belong to user with id=" + userId);
        }
    }
}
