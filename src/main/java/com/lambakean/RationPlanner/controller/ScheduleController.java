package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDto) {

        ScheduleDto createdScheduleDto = scheduleService.createSchedule(scheduleDto);

        return new ResponseEntity<>(createdScheduleDto, HttpStatus.CREATED);
    }
}

