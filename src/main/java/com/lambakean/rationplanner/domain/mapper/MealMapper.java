package com.lambakean.rationplanner.domain.mapper;

import com.lambakean.rationplanner.data.model.Meal;
import com.lambakean.rationplanner.representation.dto.MealDto;
import com.lambakean.rationplanner.representation.dto.TimeDto;
import com.lambakean.rationplanner.representation.dto.form.MealCreationForm;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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
