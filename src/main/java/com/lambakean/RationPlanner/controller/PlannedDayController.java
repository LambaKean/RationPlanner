package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.dto.form.PlannedDayCreationForm;
import com.lambakean.RationPlanner.mapper.PlannedDayMapper;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.service.PlannedDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/plannedDay")
@RequiredArgsConstructor
public class PlannedDayController {

    private final PlannedDayService plannedDayService;
    private final PlannedDayMapper plannedDayMapper;

    @PostMapping
    public ResponseEntity<PlannedDayDto> createPlannedDay(@RequestBody PlannedDayCreationForm plannedDayCreationForm) {

        PlannedDay plannedDayData = plannedDayMapper.toPlannedDay(plannedDayCreationForm);
        PlannedDay createdPlannedDay = plannedDayService.createPlannedDay(plannedDayData);

        return ResponseEntity.ok(plannedDayMapper.toPlannedDayDto(createdPlannedDay));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlannedDayDto> getPlannedDayById(@PathVariable String id) {

        PlannedDay plannedDay = plannedDayService.getPlannedDayById(id);

        return ResponseEntity.ok(plannedDayMapper.toPlannedDayDto(plannedDay));
    }

    @GetMapping
    public ResponseEntity<Set<PlannedDayDto>> getPlannedDays() {

        Set<PlannedDay> plannedDays = plannedDayService.getCurrentUserPlannedDays();

        return ResponseEntity.ok(
                plannedDays
                        .stream()
                        .map(plannedDayMapper::toPlannedDayDto)
                        .collect(Collectors.toSet())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlannedDayById(@PathVariable String id) {

        this.plannedDayService.deletePlannedDayById(id);

        return ResponseEntity.noContent().build();
    }
}
