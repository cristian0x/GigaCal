package com.gigacal.service.impl;

import com.gigacal.dto.CalendarDTO;
import com.gigacal.entity.CalendarEntity;
import com.gigacal.entity.UserEntity;
import com.gigacal.exception.CalendarException;
import com.gigacal.exception.ForbiddenActionException;
import com.gigacal.mappers.CalendarMapper;
import com.gigacal.repository.CalendarRepository;
import com.gigacal.service.ICalendarService;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class CalendarServiceImpl implements ICalendarService {

    private final Logger LOGGER = LoggerFactory.getLogger(CalendarServiceImpl.class);

    private final CalendarRepository calendarRepository;
    private final UserServiceImpl userService;

    @Override
    public CalendarDTO createCalendar(final CalendarDTO calendarDTO, final Authentication authentication) {
        final UserEntity loggedUser = this.userService.getUserFromAuthentication(authentication);
        final CalendarEntity calendarEntity = CalendarMapper.INSTANCE.map(calendarDTO);
        calendarEntity.setUser(loggedUser);
        calendarEntity.setCreateDate(LocalDateTime.now());
        return CalendarMapper.INSTANCE.map(this.calendarRepository.save(calendarEntity));
    }

    @Override
    public CalendarEntity findCalendarById(final Long calendarId, final Authentication authentication) {
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);
        validateThatCalendarBelongsToUser(calendarId, loggedInUser.getId());

        return calendarRepository.findById(calendarId).orElseThrow(() ->
                new NoResultException("calendar with id " + calendarId + " not found"));
    }

    @Override
    public List<CalendarDTO> findCalendarsForUser(final Authentication authentication) {
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);
        final List<CalendarEntity> calendarEntities = calendarRepository.findCalendarsByUserId(loggedInUser.getId()).orElse(emptyList());
        return calendarEntities.stream().map(CalendarMapper.INSTANCE::map).toList();
    }

    @Override
    public List<CalendarEntity> findCalendarsByName(final String name, final Authentication authentication) {
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);
        validateThatCalendarBelongsToUser(name, loggedInUser.getId());

        return calendarRepository.findCalendarsByName(name).orElseThrow(() ->
                new NoResultException("calendars for name " + name + " not found"));
    }

    @Override
    public void deleteCalendar(final Long calendarId, final Authentication authentication) {
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);
        validateThatCalendarBelongsToUser(calendarId, loggedInUser.getId());

        if (!calendarRepository.existsById(calendarId)) {
            throw new NoResultException("calendar with id " + calendarId + " not found");
        }
        calendarRepository.deleteById(calendarId);
    }

    @Override
    @Transactional
    public CalendarDTO updateCalendar(final CalendarDTO calendar, final Authentication authentication) {
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);
        validateThatCalendarBelongsToUser(calendar.id(), loggedInUser.getId());

        CalendarEntity calendarToUpdate = calendarRepository.findById(calendar.id()).orElseThrow(() ->
                new NoResultException("calendar with id " + calendar.id() + " not found"));

        if (!calendar.name().isEmpty()) {
            calendarToUpdate.setName(calendar.name());
        }
        else {
            throw new CalendarException.IncorrectDataProvided();
        }

        calendarToUpdate.setUpdateDate(LocalDateTime.now());
        return CalendarMapper.INSTANCE.map(calendarRepository.save(calendarToUpdate));
    }

    @Override
    public List<CalendarEntity> findAllUserCalendars(final Authentication authentication) {
        final UserEntity loggedUser = this.userService.getUserFromAuthentication(authentication);
        return this.calendarRepository.findAllByUser(loggedUser);
    }

    @Override
    public void validateThatCalendarBelongsToUser(final Long calendarId, final Long userId) {
        if (this.calendarRepository.findByIdAndUserId(calendarId, userId).isEmpty())  {
            LOGGER.warn("Calendar with id={} doesn't belong to user with id={}", calendarId, userId);
            throw new ForbiddenActionException("Calendar with id=" + calendarId + " doesn't belong to user with id=" + userId);
        }
    }

    @Override
    public void validateThatCalendarBelongsToUser(final String name, final Long userId) {
        if (this.calendarRepository.findByCalendarNameAndUserId(name, userId).isEmpty())  {
            LOGGER.warn("Calendar with name={} doesn't belong to user with id={}", name, userId);
            throw new ForbiddenActionException("Calendar with name=" + name + " doesn't belong to user with id=" + userId);
        }
    }
}
