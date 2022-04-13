package com.lambakean.RationPlanner.domain.mapper;

import com.lambakean.RationPlanner.data.model.MeasurementUnit;
import com.lambakean.RationPlanner.representation.dto.MeasurementUnitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper {

    MeasurementUnitDto toMeasurementUnitDto(MeasurementUnit measurementUnit);
}
