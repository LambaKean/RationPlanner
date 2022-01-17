package com.lambakean.RationPlanner.dto;

import com.lambakean.RationPlanner.model.Ingredient;
import com.lambakean.RationPlanner.model.Meal;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

public class MealDto {

    private String id;
    private String name;
    private String description;
    private DurationDto cookingDuration;
    private String recipe;
    private Set<IngredientDto> ingredients;
    private Double price;

    public MealDto(String id,
                   String name,
                   String description,
                   DurationDto cookingDuration,
                   String recipe,
                   Set<IngredientDto> ingredients,
                   Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cookingDuration = cookingDuration;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.price = price;
    }

    public MealDto() {}

    public Meal toMeal() {

        Meal meal = new Meal();

        meal.setId(id);
        meal.setName(name);
        meal.setDescription(description);
        meal.setRecipe(recipe);

        if(cookingDuration != null) {

            int durationMinutes = 0;

            if(cookingDuration.getMinutes() != null) {
                durationMinutes += cookingDuration.getMinutes();
            }
            if(cookingDuration.getHours() != null) {
                durationMinutes += cookingDuration.getHours() * 60;
            }

            Duration duration = Duration.ofMinutes(durationMinutes);
            meal.setCookingDuration(duration);
        }

        meal.setIngredients(
                ingredients
                    .stream()
                    .map(IngredientDto::toIngredient)
                    .collect(Collectors.toSet())
        );

        for(Ingredient ingredient : meal.getIngredients()) {
            ingredient.setMeal(meal);
        }

        return meal;
    }

    public static MealDto fromMeal(Meal meal) {

        if (meal == null) {
            return null;
        }

        MealDto mealDto = new MealDto();

        mealDto.setId(meal.getId());
        mealDto.setName(meal.getName());
        mealDto.setDescription(meal.getDescription());
        mealDto.setCookingDuration(
                DurationDto.fromDuration(meal.getCookingDuration())
        );
        mealDto.setRecipe(meal.getRecipe());
        mealDto.setIngredients(
                meal.getIngredients()
                        .stream()
                        .map(IngredientDto::fromIngredient)
                        .collect(Collectors.toSet())
        );
        mealDto.setPrice(meal.getPrice());

        return mealDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DurationDto getCookingDuration() {
        return cookingDuration;
    }

    public void setCookingDuration(DurationDto cookingDuration) {
        this.cookingDuration = cookingDuration;
    }

    public Set<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
