package com.gigacal.controller;

import com.gigacal.dto.CalendarDTO;
import com.gigacal.entity.CalendarEntity;
import com.gigacal.service.impl.CalendarServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Calendar", description = "Calendar API")
@RestController
@RequestMapping(path = "/calendars", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@CrossOrigin
public class CalendarController {

    private final CalendarServiceImpl calendarService;

    @Operation(summary = "Get calendar by id")
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getCalendarById(@PathVariable("id") final Long id,
                                             final Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.findCalendarById(id, authentication));
    }

    @Operation(summary = "Get all calendars by user id")
    @GetMapping
    public ResponseEntity<List<CalendarDTO>> getCalendarsForUser(final Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(this.calendarService.findCalendarsForUser(authentication));
    }

    @Operation(summary = "Get calendars by name")
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<?> getCalendarsByName(@PathVariable("name") final String name,
                                                final Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.findCalendarsByName(name, authentication));
    }

    @Operation(summary = "Create calendar")
    @PostMapping
    public ResponseEntity<CalendarDTO> createCalendar(@RequestBody final CalendarDTO calendarDTO,
                                                      final Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.createCalendar(calendarDTO, authentication));
    }

    @Operation(summary = "Delete calendar")
    @DeleteMapping(path = "{calendarId}")
    public ResponseEntity<?> deleteCalendar(@PathVariable("calendarId") final Long calendarId,
                                            final Authentication authentication) {
        calendarService.deleteCalendar(calendarId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Calendar deleted successfully.");
    }

    @Operation(summary = "Update calendar")
    @PutMapping
    public ResponseEntity<CalendarDTO> updateCalendar(@RequestBody final CalendarDTO calendar,
                                                      final Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(this.calendarService.updateCalendar(calendar, authentication));
    }
}
