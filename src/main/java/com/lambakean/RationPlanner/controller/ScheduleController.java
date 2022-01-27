package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.dto.ScheduledPlannedDayDto;
import com.lambakean.RationPlanner.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

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

    @GetMapping()
    public ResponseEntity<Set<ScheduledPlannedDayDto>> getMonthSchedule(
            @RequestParam("date") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate date
    ) {

        return new ResponseEntity<>(
                scheduleService.getMonthSchedule(date),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable String id) {

        ScheduleDto scheduleDto = this.scheduleService.getScheduleById(id);

        return new ResponseEntity<>(scheduleDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable String id) {

        this.scheduleService.deleteScheduleById(id);

        return ResponseEntity.noContent().build();
    }
}

