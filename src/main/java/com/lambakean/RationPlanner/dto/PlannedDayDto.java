package com.lambakean.RationPlanner.dto;

import java.util.List;

public class PlannedDayDto {

    private String id;
    private String name;
    private Double price;
    private Integer amountOfMeals;
    private List<PlannedDayMealDto> plannedDayMeals;


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

    public List<PlannedDayMealDto> getPlannedDayMeals() {
        return plannedDayMeals;
    }

    public void setPlannedDayMeals(List<PlannedDayMealDto> plannedDayMealDtos) {
        this.plannedDayMeals = plannedDayMealDtos;
    }
}
