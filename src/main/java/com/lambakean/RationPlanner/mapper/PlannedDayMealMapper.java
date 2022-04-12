package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.PlannedDayMealDto;
import com.lambakean.RationPlanner.dto.TimeDto;
import com.lambakean.RationPlanner.dto.form.PlannedDayCreationForm;
import com.lambakean.RationPlanner.model.PlannedDayMeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PlannedDayMealMapper {

    @Autowired
    protected DateAndTimeMapper dateAndTimeMapper;

    public abstract List<PlannedDayMeal> toPlannedDayMeals(
            List<PlannedDayCreationForm.PlannedDayMealInformation> plannedDayMealInformation
    );

    public abstract List<PlannedDayMealDto> toPlannedDayMealDtos(List<PlannedDayMeal> plannedDayMeals);

    protected LocalTime map(TimeDto timeDto) {
        return dateAndTimeMapper.toLocalTime(timeDto);
    }

    protected TimeDto map(LocalTime localTime) {
        return dateAndTimeMapper.toTimeDto(localTime);
    }
}
