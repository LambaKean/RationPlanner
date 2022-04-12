package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.dto.PlannedDayMealDto;
import com.lambakean.RationPlanner.dto.form.PlannedDayCreationForm;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.model.PlannedDayMeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
