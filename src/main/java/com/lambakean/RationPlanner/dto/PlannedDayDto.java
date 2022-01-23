package com.lambakean.RationPlanner.dto;

import java.util.Set;

public class PlannedDayDto {

    private String id;
    private String name;
    private String userId;
    private Double price;
    private Integer amountOfMeals;
    private Set<PlannedDayMealDto> plannedDayMealDtos;

    public PlannedDayDto(String id,
                         String name,
                         String userId,
                         Double price,
                         Integer amountOfMeals,
                         Set<PlannedDayMealDto> plannedDayMealDtos) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.price = price;
        this.amountOfMeals = amountOfMeals;
        this.plannedDayMealDtos = plannedDayMealDtos;
    }

    public PlannedDayDto() {}


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmountOfMeals() {
        return amountOfMeals;
    }

    public void setAmountOfMeals(Integer amountOfMeals) {
        this.amountOfMeals = amountOfMeals;
    }

    public Set<PlannedDayMealDto> getPlannedDayMeals() {
        return plannedDayMealDtos;
    }

    public void setPlannedDayMeals(Set<PlannedDayMealDto> plannedDayMealDtos) {
        this.plannedDayMealDtos = plannedDayMealDtos;
    }
}
