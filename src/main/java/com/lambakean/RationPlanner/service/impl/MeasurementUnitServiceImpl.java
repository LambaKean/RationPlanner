package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.model.MeasurementUnit;
import com.lambakean.RationPlanner.repository.MeasurementUnitRepository;
import com.lambakean.RationPlanner.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementUnitServiceImpl implements MeasurementUnitService {

    private final MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    public MeasurementUnitServiceImpl(MeasurementUnitRepository measurementUnitRepository) {
        this.measurementUnitRepository = measurementUnitRepository;
    }

    @Override
    public List<MeasurementUnit> getUnits() {
        return measurementUnitRepository.findAll();
    }
}
