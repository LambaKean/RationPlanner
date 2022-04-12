package com.lambakean.RationPlanner.representation.controller;

import com.lambakean.RationPlanner.representation.dto.MealDto;
import com.lambakean.RationPlanner.representation.dto.form.MealCreationForm;
import com.lambakean.RationPlanner.domain.mapper.MealMapper;
import com.lambakean.RationPlanner.data.model.Meal;
import com.lambakean.RationPlanner.domain.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final MealMapper mealMapper;

    @PostMapping
    public ResponseEntity<MealDto> createMeal(@RequestBody MealCreationForm mealCreationForm) {

        Meal createdMeal = mealService.createMeal(mealMapper.toMeal(mealCreationForm));
        MealDto createdMealDto = mealMapper.toMealDto(createdMeal);

        return ResponseEntity.ok(createdMealDto);
    }

    @GetMapping
    public ResponseEntity<List<MealDto>> getMeals() {

        List<Meal> currentUserMeals = mealService.getCurrentUserMeals();

        return ResponseEntity.ok(
                currentUserMeals
                        .stream()
                        .map(mealMapper::toMealDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable String id) {

        Meal meal = mealService.getMealById(id);

        return ResponseEntity.ok(mealMapper.toMealDto(meal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealById(@PathVariable String id) {

        mealService.deleteMealById(id);

        return ResponseEntity.noContent().build();
    }
}
