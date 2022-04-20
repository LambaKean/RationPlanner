package com.lambakean.rationplanner.domain.service.impl;

import com.lambakean.rationplanner.data.model.MeasurementUnit;
import com.lambakean.rationplanner.data.repository.MeasurementUnitRepository;
import com.lambakean.rationplanner.domain.service.MeasurementUnitService;
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
