package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.MealDto;
import com.lambakean.RationPlanner.dto.TimeDto;
import com.lambakean.RationPlanner.dto.form.MealCreationForm;
import com.lambakean.RationPlanner.model.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.time.Duration;

@Mapper(componentModel = "spring")
public abstract class MealMapper {

    @Autowired
    protected DateAndTimeMapper dateAndTimeMapper;

    public abstract Meal toMeal(MealCreationForm mealCreationForm);

    public abstract MealDto toMealDto(Meal meal);


    protected Duration map(TimeDto timeDto) {
        return dateAndTimeMapper.toDuration(timeDto);
    }

    protected TimeDto map(Duration duration) {
        return dateAndTimeMapper.toTimeDto(duration);
    }
}
