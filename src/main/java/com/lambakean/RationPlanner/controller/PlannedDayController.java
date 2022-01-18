package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.service.PlannedDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plannedDay")
public class PlannedDayController {

    private final PlannedDayService plannedDayService;

    @Autowired
    public PlannedDayController(PlannedDayService plannedDayService) {
        this.plannedDayService = plannedDayService;
    }

    @PostMapping
    public ResponseEntity<PlannedDayDto> createPlannedDay(@RequestBody PlannedDayDto plannedDayDto) {

        PlannedDayDto createdPlannedDay = plannedDayService.createPlannedDay(plannedDayDto);

        return new ResponseEntity<>(createdPlannedDay, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlannedDayDto> getPlannedDayById(@PathVariable String id) {

        PlannedDayDto plannedDayDto = plannedDayService.getPlannedDayById(id);

        return new ResponseEntity<>(plannedDayDto, HttpStatus.OK);
    }
}
