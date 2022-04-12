package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.MeasurementUnitDto;
import com.lambakean.RationPlanner.model.MeasurementUnit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper {

    MeasurementUnitDto toMeasurementUnitDto(MeasurementUnit measurementUnit);
}
