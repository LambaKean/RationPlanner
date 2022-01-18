package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.MeasurementUnitDto;
import com.lambakean.RationPlanner.model.MeasurementUnit;
import com.lambakean.RationPlanner.repository.MeasurementUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeasurementUnitDtoConverter {

    private final MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    public MeasurementUnitDtoConverter(MeasurementUnitRepository measurementUnitRepository) {
        this.measurementUnitRepository = measurementUnitRepository;
    }

    public MeasurementUnit toMeasurementUnit(MeasurementUnitDto measurementUnitDto) {

        if(measurementUnitDto == null) {
            return null;
        }

        MeasurementUnit measurementUnit = new MeasurementUnit();

        if(measurementUnitDto.getId() != null) {
            return measurementUnitRepository.findById(measurementUnitDto.getId()).orElse(null);
        }

        measurementUnit.setName(measurementUnitDto.getName());

        return measurementUnit;
    }

    public MeasurementUnitDto toMeasurementUnitDto(MeasurementUnit measurementUnit) {

        if(measurementUnit == null) {
            return null;
        }

        MeasurementUnitDto measurementUnitDto = new MeasurementUnitDto();

        measurementUnitDto.setId(measurementUnit.getId());
        measurementUnitDto.setName(measurementUnit.getName());

        return measurementUnitDto;
    }

}
