package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.MeasurementUnitDto;
import com.lambakean.RationPlanner.mapper.MeasurementUnitMapper;
import com.lambakean.RationPlanner.model.MeasurementUnit;
import com.lambakean.RationPlanner.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/measurementUnit")
public class MeasurementUnitController {

    private final MeasurementUnitService measurementUnitService;
    private final MeasurementUnitMapper measurementUnitMapper;

    @Autowired
    public MeasurementUnitController(MeasurementUnitService measurementUnitService,
                                     MeasurementUnitMapper measurementUnitMapper) {
        this.measurementUnitService = measurementUnitService;
        this.measurementUnitMapper = measurementUnitMapper;
    }

    @GetMapping
    public ResponseEntity<List<MeasurementUnitDto>> getUnits() {

        List<MeasurementUnit> measurementUnits = measurementUnitService.getUnits();

        return ResponseEntity.ok(
                measurementUnits.stream()
                        .map(measurementUnitMapper::toMeasurementUnitDto)
                        .collect(Collectors.toList())
        );
    }
}
