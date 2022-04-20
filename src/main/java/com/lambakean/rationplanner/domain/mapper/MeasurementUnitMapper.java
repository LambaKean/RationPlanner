package com.lambakean.rationplanner.domain.mapper;

import com.lambakean.rationplanner.data.model.MeasurementUnit;
import com.lambakean.rationplanner.representation.dto.MeasurementUnitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper {

    MeasurementUnitDto toMeasurementUnitDto(MeasurementUnit measurementUnit);
}
