package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.dto.MeasurementUnitDto;
import com.lambakean.RationPlanner.dto.converter.MeasurementUnitDtoConverter;
import com.lambakean.RationPlanner.repository.MeasurementUnitRepository;
import com.lambakean.RationPlanner.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementUnitServiceImpl implements MeasurementUnitService {

    private final MeasurementUnitRepository measurementUnitRepository;
    private final MeasurementUnitDtoConverter measurementUnitDtoConverter;

    @Autowired
    public MeasurementUnitServiceImpl(MeasurementUnitRepository measurementUnitRepository,
                                      MeasurementUnitDtoConverter measurementUnitDtoConverter) {
        this.measurementUnitRepository = measurementUnitRepository;
        this.measurementUnitDtoConverter = measurementUnitDtoConverter;
    }

    @Override
    public List<MeasurementUnitDto> getUnits() {

        return this.measurementUnitRepository.findAll()
                .stream()
                .map(measurementUnitDtoConverter::toMeasurementUnitDto)
                .collect(Collectors.toList());
    }
}