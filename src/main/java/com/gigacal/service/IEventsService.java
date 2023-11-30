package com.gigacal.service;

import com.gigacal.dto.EventDto;
import com.gigacal.entity.EventEntity;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface IEventsService {

    EventDto getEventDto(Long eventId, Authentication authentication);

    void createEvent(EventDto eventDto, Authentication authentication);

    void deleteEvent(Long eventId, Authentication authentication);

    void editEvent(Long eventId, EventDto eventDto, Authentication authentication);

    UUID shareEvent(Long eventId, Authentication authentication);

    EventDto getSharedEvent(UUID uuid, Authentication authentication);

    EventEntity getEventById(Long eventId);

    EventEntity getEventByUuid(UUID uuid);
}
