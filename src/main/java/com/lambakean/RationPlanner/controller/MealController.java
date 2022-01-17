package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.MealDto;
import com.lambakean.RationPlanner.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meal")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    public ResponseEntity<MealDto> createMeal(@RequestBody MealDto mealDto) {

        MealDto outgoingMealDto = mealService.createMeal(mealDto);

        return new ResponseEntity<>(outgoingMealDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MealDto>> getMeals() {

        List<MealDto> mealDtos = mealService.getCurrentUserMeals();

        return new ResponseEntity<>(mealDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable String id) {

        MealDto mealDto = mealService.getMealById(id);

        return new ResponseEntity<>(mealDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealById(@PathVariable String id) {

        mealService.deleteMealById(id);

        return ResponseEntity.noContent().build();
    }
}
