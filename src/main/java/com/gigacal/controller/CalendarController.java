package com.gigacal.controller;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.service.ICalendarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/calendars", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CalendarController {

    private ICalendarService iCalendarService;

    @GetMapping
    public ResponseEntity<?> getCalendar() {
        return ResponseEntity.status(HttpStatus.OK).body(iCalendarService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createCalendar(@RequestBody CalendarEntity calendarEntity) {
        iCalendarService.createCalendar(calendarEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Calendar created successfully.");
    }
}
