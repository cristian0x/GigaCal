package com.gigacal.controller;

import com.gigacal.dto.EventDto;
import com.gigacal.service.impl.EventsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Events", description = "Events API")
@RestController
@RequestMapping(path = "/events", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class EventsController {

    final EventsServiceImpl eventsService;

    @Operation(summary = "Get event by id")
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEventDto(@PathVariable final Long eventId,
                                                final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.getEventDto(eventId, authentication));
    }

    @Operation(summary = "Create new event")
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid final EventDto eventDto,
                                         final Authentication authentication) {
        this.eventsService.createEvent(eventDto, authentication);
        return ResponseEntity.ok("Successfully created an event");
    }

    @Operation(summary = "Delete event by id")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable final Long eventId,
                                         final Authentication authentication) {
        this.eventsService.deleteEvent(eventId, authentication);
        return ResponseEntity.ok("Successfully deleted an event");
    }

    @Operation(summary = "Update event")
    @PutMapping("/{eventId}")
    public ResponseEntity<?> editEvent(@PathVariable final Long eventId,
                                       @RequestBody @Valid final EventDto eventDto,
                                       final Authentication authentication) {
        this.eventsService.editEvent(eventId, eventDto, authentication);
        return ResponseEntity.ok("Successfully edited an event");
    }

    @Operation(summary = "Share event by id")
    @GetMapping("/share/{eventId}")
    public ResponseEntity<UUID> shareEvent(@PathVariable final Long eventId,
                                           final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.shareEvent(eventId, authentication));
    }

    @Operation(summary = "Get shared event by uuid")
    @GetMapping("/shared/{uuid}")
    public ResponseEntity<EventDto> getSharedEvent(@PathVariable final UUID uuid,
                                                   final Authentication authentication) {
        return ResponseEntity.ok(this.eventsService.getSharedEvent(uuid, authentication));
    }
}
