package com.gigacal.controller;

import com.gigacal.dto.EventDTO;
import com.gigacal.service.impl.EventsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Events", description = "Events API")
@RestController
@RequestMapping(path = "/events", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@CrossOrigin("*")
public class EventsController {

    private final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    final EventsServiceImpl eventsService;

    @Operation(summary = "Get event by id")
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventDto(@PathVariable final Long eventId,
                                                final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.getEventDto(eventId, authentication));
    }

    @Operation(summary = "Create new event")
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid final EventDTO eventDto,
                                                final Authentication authentication) {
        LOGGER.info("Creating event for eventDto={}", eventDto);
        return ResponseEntity.ok(this.eventsService.createEvent(eventDto, authentication));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllUserEvents(final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.getAllUserEvents(authentication));
    }

    @Operation(summary = "Delete event by id")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable final Long eventId,
                                         final Authentication authentication) {
        this.eventsService.deleteEvent(eventId, authentication);
        return ResponseEntity.ok("Successfully deleted an event");
    }

    @Operation(summary = "Update event")
    @PutMapping
    public ResponseEntity<EventDTO> editEvent(@RequestBody @Valid final EventDTO eventDto,
                                              final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.editEvent(eventDto, authentication));
    }

    @Operation(summary = "Share event by id")
    @GetMapping("/share/{eventId}")
    public ResponseEntity<UUID> shareEvent(@PathVariable final Long eventId,
                                           final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.shareEvent(eventId, authentication));
    }

    @Operation(summary = "Get shared event by uuid")
    @GetMapping("/shared/{uuid}")
    public ResponseEntity<EventDTO> getSharedEvent(@PathVariable final UUID uuid,
                                                   final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.getSharedEvent(uuid, authentication));
    }
}
