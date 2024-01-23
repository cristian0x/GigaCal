package com.gigacal.service;

import com.gigacal.dto.EventDTO;
import com.gigacal.entity.EventEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface IEventsService {

    EventDTO getEventDto(Long eventId, Authentication authentication);

    List<EventDTO> getAllUserEvents(Authentication authentication);

    EventDTO createEvent(EventDTO eventDto, Authentication authentication);

    void deleteEvent(Long eventId, Authentication authentication);

    EventDTO editEvent(EventDTO eventDto, Authentication authentication);

    UUID shareEvent(Long eventId, Authentication authentication);

    EventDTO getSharedEvent(UUID uuid, Authentication authentication);

    EventEntity getEventById(Long eventId);

    EventEntity getEventByUuid(UUID uuid);
}
