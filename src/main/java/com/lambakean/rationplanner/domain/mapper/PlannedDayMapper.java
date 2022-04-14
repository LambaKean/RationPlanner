package com.lambakean.rationplanner.domain.mapper;

import com.lambakean.rationplanner.data.model.PlannedDay;
import com.lambakean.rationplanner.representation.dto.PlannedDayDto;
import com.lambakean.rationplanner.representation.dto.form.PlannedDayCreationForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PlannedDayMapper {

    @Autowired
    protected PlannedDayMealMapper plannedDayMealMapper;

    @Mapping(
            target = "plannedDayMeals",
            expression = "java(plannedDayMealMapper.toPlannedDayMeals(plannedDayCreationForm.getPlannedDayMeals()))"
    )
    public abstract PlannedDay toPlannedDay(PlannedDayCreationForm plannedDayCreationForm);

    @Mapping(
            target = "plannedDayMeals",
            expression = "java(plannedDayMealMapper.toPlannedDayMealDtos(plannedDay.getPlannedDayMeals()))"
    )
    public abstract PlannedDayDto toPlannedDayDto(PlannedDay plannedDay);
}
