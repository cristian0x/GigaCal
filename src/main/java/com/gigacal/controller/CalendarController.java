package com.gigacal.controller;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.service.impl.CalendarServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/calendars", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CalendarController {

    private final CalendarServiceImpl calendarService;

    @GetMapping
    public ResponseEntity<?> getCalendar() {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getCalendarById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.findCalendarById(id));
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<?> getCalendarsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.findCalendarsByUserId(userId));
    }

    @GetMapping(path = "/{column}/{parameter}")
    public ResponseEntity<?> getCalendarsByExactColumn(@PathVariable("column") String column,
                                                       @PathVariable("parameter") String parameter) {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.getCalendarsByExactColumn(column, parameter));
    }

    @PostMapping
    public ResponseEntity<?> createCalendar(@RequestBody final CalendarEntity calendarEntity) {
        calendarService.createCalendar(calendarEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Calendar created successfully.");
    }

    @DeleteMapping(path = "{calendarId}")
    public ResponseEntity<?> deleteCalendar(@PathVariable("calendarId") Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Calendar deleted successfully.");
    }

    @PutMapping(path = "{calendarId}")
    public ResponseEntity<?> updateCalendar(@PathVariable("calendarId") Long calendarId,
                                            @RequestBody CalendarEntity calendar) {
        calendarService.updateCalendar(calendarId, calendar);
        return ResponseEntity.status(HttpStatus.OK).body("Calendar updated successfully.");
    }
}
