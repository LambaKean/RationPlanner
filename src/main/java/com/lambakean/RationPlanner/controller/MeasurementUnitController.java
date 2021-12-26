package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.MeasurementUnitDto;
import com.lambakean.RationPlanner.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/measurementUnit")
public class MeasurementUnitController {

    private final MeasurementUnitService measurementUnitService;

    @Autowired
    public MeasurementUnitController(MeasurementUnitService measurementUnitService) {
        this.measurementUnitService = measurementUnitService;
    }

    @GetMapping
    public ResponseEntity<List<MeasurementUnitDto>> getUnits() {

        return new ResponseEntity<>(measurementUnitService.getUnits(), HttpStatus.OK);
    }
}
