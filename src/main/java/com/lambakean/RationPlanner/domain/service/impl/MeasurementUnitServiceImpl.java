package com.lambakean.RationPlanner.domain.service.impl;

import com.lambakean.RationPlanner.data.model.MeasurementUnit;
import com.lambakean.RationPlanner.data.repository.MeasurementUnitRepository;
import com.lambakean.RationPlanner.domain.service.MeasurementUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementUnitServiceImpl implements MeasurementUnitService {

    private final MeasurementUnitRepository measurementUnitRepository;

    @Override
    public List<MeasurementUnit> getUnits() {
        return measurementUnitRepository.findAll();
    }
}
