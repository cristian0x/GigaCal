package com.gigacal.controller;

import com.gigacal.dto.EventDto;
import com.gigacal.service.impl.EventsServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/event", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class EventsController {

    final EventsServiceImpl eventsService;

    @GetMapping
    public ResponseEntity<EventDto> getEventDto(@RequestParam final Long eventId,
                                                final Authentication authentication) {
        final EventDto eventDto = this.eventsService.getEventDto(eventId, authentication);
        return ResponseEntity.ok(eventDto);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid final EventDto eventDto,
                                         final Authentication authentication) {
        this.eventsService.createEvent(eventDto, authentication);
        return ResponseEntity.ok("Successfully created an event");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEvent(@RequestParam final Long eventId,
                                         final Authentication authentication) {
        this.eventsService.deleteEvent(eventId, authentication);
        return ResponseEntity.ok("Successfully deleted an event");
    }

    @PutMapping
    public ResponseEntity<?> editEvent(@RequestParam final Long eventId,
                                       @RequestBody @Valid final EventDto eventDto,
                                       final Authentication authentication) {
        this.eventsService.editEvent(eventId, eventDto, authentication);
        return ResponseEntity.ok("Successfully edited an event");
    }

    @GetMapping("/share")
    public ResponseEntity<UUID> shareEvent(@RequestParam final Long eventId,
                                           final Authentication authentication) {
        final UUID uuid = this.eventsService.shareEvent(eventId, authentication);
        return ResponseEntity.ok(uuid);
    }

    @GetMapping("/share/{uuid}")
    public ResponseEntity<EventDto> getSharedEvent(@PathVariable final UUID uuid,
                                                   final Authentication authentication) {
        final EventDto eventDto = this.eventsService.getSharedEvent(uuid, authentication);
        return ResponseEntity.ok(eventDto);
    }
}
