package com.gigacal.service.impl;

import com.gigacal.dto.EventDTO;
import com.gigacal.entity.CalendarEntity;
import com.gigacal.entity.EventEntity;
import com.gigacal.entity.UserEntity;
import com.gigacal.exception.EventNotFoundException;
import com.gigacal.mappers.EventMapper;
import com.gigacal.repository.EventRepository;
import com.gigacal.service.IEventsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventsServiceImpl implements IEventsService {

    private final Logger LOGGER = LoggerFactory.getLogger(EventsServiceImpl.class);

    private final EventRepository eventRepository;
    private final UserServiceImpl userService;
    private final CalendarServiceImpl calendarService;

    @Override
    public EventDTO getEventDto(final Long eventId, final Authentication authentication) {
        LOGGER.info("Getting an event with id={}", eventId);
        final EventEntity eventEntity = this.getEventByIdAndValidateThatCalendarBelongsToUser(eventId, authentication);
        return EventMapper.INSTANCE.map(eventEntity);
    }

    @Override
    public List<EventDTO> getAllUserEvents(final Authentication authentication) {
        final List<CalendarEntity> calendarEntities = this.calendarService.findAllUserCalendars(authentication);
        final List<EventEntity> eventEntities = this.eventRepository.findAllByCalendarIdIn(calendarEntities.stream().map(CalendarEntity::getId).toList());
        return eventEntities.stream().map(EventMapper.INSTANCE::map).toList();
    }

    @Override
    public EventDTO createEvent(final EventDTO eventDto, final Authentication authentication) {
        LOGGER.info("Creating an event for eventDto={}", eventDto);
        final EventEntity eventEntity = this.mapEventDtoToEntityAndValidateThatCalendarBelongsToUser(eventDto, authentication);

        eventEntity.setCreateDate(LocalDateTime.now());
        eventEntity.setUuid(UUID.randomUUID());

        LOGGER.info("Saving an eventEntity={}", eventEntity);
        return EventMapper.INSTANCE.map(this.eventRepository.save(eventEntity));
    }

    @Override
    public void deleteEvent(final Long eventId, final Authentication authentication) {
        LOGGER.info("Deleting an event with id={}", eventId);
        this.getEventByIdAndValidateThatCalendarBelongsToUser(eventId, authentication);
        this.eventRepository.deleteById(eventId);
    }

    @Override
    public EventDTO editEvent(final EventDTO eventDto, final Authentication authentication) {
        LOGGER.info("Editing an event with id={} and eventDto={}", eventDto.id(), eventDto);
        final EventEntity newEventEntity = this.mapEventDtoToEntityAndValidateThatCalendarBelongsToUser(eventDto, authentication);

        newEventEntity.setUpdateDate(LocalDateTime.now());
        return EventMapper.INSTANCE.map(this.eventRepository.save(newEventEntity));
    }

    @Override
    public UUID shareEvent(final Long eventId, final Authentication authentication) {
        LOGGER.info("Sharing an event with eventId={}", eventId);
        final EventEntity eventEntity = this.getEventByIdAndValidateThatCalendarBelongsToUser(eventId, authentication);
        return eventEntity.getUuid();
    }

    @Override
    public EventDTO getSharedEvent(final UUID uuid, final Authentication authentication) {
        LOGGER.info("Getting an event with UUID={}", uuid);
        final EventEntity eventEntity = this.getEventByUuid(uuid);
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);

        this.calendarService.validateThatCalendarBelongsToUser(eventEntity.getCalendarId(), loggedInUser.getId());

        return EventMapper.INSTANCE.map(eventEntity);
    }

    @Override
    public EventEntity getEventById(final Long eventId) {
        final Optional<EventEntity> optionalEventEntity = this.eventRepository.findById(eventId);

        if (optionalEventEntity.isEmpty()) {
            LOGGER.warn("Event with eventId={} not found", eventId);
            throw new EventNotFoundException("Event with id=" + eventId + " not found");
        }

        return optionalEventEntity.get();
    }

    @Override
    public EventEntity getEventByUuid(final UUID uuid) {
        final Optional<EventEntity> optionalEventEntity = this.eventRepository.findByUuid(uuid);

        if (optionalEventEntity.isEmpty()) {
            LOGGER.warn("Event with uuid={} not found", uuid);
            throw new EventNotFoundException("Event with uuid=" + uuid + " not found");
        }

        return optionalEventEntity.get();
    }

    private EventEntity getEventByIdAndValidateThatCalendarBelongsToUser(final Long eventId, final Authentication authentication) {
        final EventEntity eventEntity = this.getEventById(eventId);
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);
        this.calendarService.validateThatCalendarBelongsToUser(eventEntity.getCalendarId(), loggedInUser.getId());

        return eventEntity;
    }

    private EventEntity mapEventDtoToEntityAndValidateThatCalendarBelongsToUser(final EventDTO eventDto, final Authentication authentication) {
        final EventEntity eventEntity = EventMapper.INSTANCE.map(eventDto);
        final UserEntity loggedInUser = this.userService.getUserFromAuthentication(authentication);
        this.calendarService.validateThatCalendarBelongsToUser(eventDto.calendarId(), loggedInUser.getId());

        return eventEntity;
    }
}
