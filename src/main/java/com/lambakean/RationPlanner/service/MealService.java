package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.MealDto;

import java.util.List;

public interface MealService {

    MealDto createMeal(MealDto mealDto);

    MealDto getMealById(String id);

    void deleteMealById(String id);

    List<MealDto> getCurrentUserMeals();

}
