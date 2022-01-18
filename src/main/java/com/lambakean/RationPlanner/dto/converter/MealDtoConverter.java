package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.MealDto;
import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MealDtoConverter {

    private final MealRepository mealRepository;
    private final TimeDtoConverter timeDtoConverter;
    private final IngredientDtoConverter ingredientDtoConverter;

    @Autowired
    public MealDtoConverter(MealRepository mealRepository,
                            TimeDtoConverter timeDtoConverter,
                            IngredientDtoConverter ingredientDtoConverter) {
        this.mealRepository = mealRepository;
        this.timeDtoConverter = timeDtoConverter;
        this.ingredientDtoConverter = ingredientDtoConverter;
    }

    public Meal toMeal(MealDto mealDto) {

        if(mealDto == null) {
            return null;
        }

        if(mealDto.getId() != null) {
            return mealRepository.findById(mealDto.getId()).orElse(null);
        }

        Meal meal = new Meal();

        meal.setName(mealDto.getName());
        meal.setDescription(mealDto.getDescription());
        meal.setCookingDuration(timeDtoConverter.toDuration(mealDto.getCookingDuration()));
        meal.setRecipe(mealDto.getRecipe());

        if(mealDto.getIngredients() != null) {
            meal.setIngredients(
                    mealDto.getIngredients()
                            .stream()
                            .map(ingredientDtoConverter::toIngredient)
                            .collect(Collectors.toSet())
            );
        }

        return meal;
    }

    public MealDto toMealDto(Meal meal) {

        if(meal == null) {
            return null;
        }

        MealDto mealDto = new MealDto();

        mealDto.setId(meal.getId());
        mealDto.setName(meal.getName());
        mealDto.setDescription(meal.getDescription());
        mealDto.setCookingDuration(timeDtoConverter.toTimeDto(meal.getCookingDuration()));
        mealDto.setRecipe(meal.getRecipe());

        if(meal.getIngredients() != null) {
            mealDto.setIngredients(
                    meal.getIngredients()
                            .stream()
                            .map(ingredientDtoConverter::toIngredientDto)
                            .collect(Collectors.toSet())
            );
        }

        mealDto.setPrice(meal.getPrice());

        return mealDto;
    }

}