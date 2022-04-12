package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.dto.ScheduledPlannedDayDto;
import com.lambakean.RationPlanner.dto.form.ScheduleCreationForm;
import com.lambakean.RationPlanner.mapper.ScheduleMapper;
import com.lambakean.RationPlanner.mapper.ScheduledPlannedDayMapper;
import com.lambakean.RationPlanner.model.Schedule;
import com.lambakean.RationPlanner.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final ScheduledPlannedDayMapper scheduledPlannedDayMapper;

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleCreationForm scheduleCreationForm) {

        Schedule createdSchedule = scheduleService.createSchedule(scheduleMapper.toSchedule(scheduleCreationForm));

        return ResponseEntity.ok(scheduleMapper.toScheduleDto(createdSchedule));
    }

    @GetMapping()
    public ResponseEntity<List<ScheduledPlannedDayDto>> getMonthSchedule(
            @RequestParam("date") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate date
    ) {

        return ResponseEntity.ok(
                scheduleService.getMonthSchedule(date)
                        .stream()
                        .map(scheduledPlannedDayMapper::toScheduledPlannedDayDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable String id) {

        Schedule schedule = scheduleService.getScheduleById(id);

        return ResponseEntity.ok(scheduleMapper.toScheduleDto(schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable String id) {

        this.scheduleService.deleteScheduleById(id);

        return ResponseEntity.noContent().build();
    }
}

