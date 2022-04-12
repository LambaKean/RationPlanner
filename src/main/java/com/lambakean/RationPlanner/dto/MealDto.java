package com.lambakean.RationPlanner.dto;

import java.util.Set;

public class MealDto {

    private String id;
    private String name;
    private String description;
    private TimeDto cookingDuration;
    private String recipe;
    private Set<IngredientDto> ingredientDtos;
    private Double price;
    private PhotoDto photoDto;


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

    public TimeDto getCookingDuration() {
        return cookingDuration;
    }

    public void setCookingDuration(TimeDto cookingDuration) {
        this.cookingDuration = cookingDuration;
    }

    public Set<IngredientDto> getIngredients() {
        return ingredientDtos;
    }

    public void setIngredients(Set<IngredientDto> ingredientDtos) {
        this.ingredientDtos = ingredientDtos;
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

    public PhotoDto getPhoto() {
        return photoDto;
    }

    public void setPhoto(PhotoDto photoDto) {
        this.photoDto = photoDto;
    }
}
