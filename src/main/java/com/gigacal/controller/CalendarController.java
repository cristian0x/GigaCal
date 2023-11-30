package com.gigacal.controller;

import com.gigacal.entity.CalendarEntity;
import com.gigacal.service.impl.CalendarServiceImpl;
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

    private final CalendarServiceImpl calendarService;

    @GetMapping
    public ResponseEntity<?> getCalendar() {
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createCalendar(@RequestBody final CalendarEntity calendarEntity) {
        calendarService.createCalendar(calendarEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Calendar created successfully.");
    }
}
