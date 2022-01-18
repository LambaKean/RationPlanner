package com.lambakean.RationPlanner.dto;

import java.util.Set;

public class PlannedDayDto {

    private String id;
    private String name;
    private String userId;
    private Set<PlannedDayMealDto> plannedDayMealDtos;

    public PlannedDayDto(String id, String name, String userId, Set<PlannedDayMealDto> plannedDayMealDtos) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public Set<PlannedDayMealDto> getPlannedDayMeals() {
        return plannedDayMealDtos;
    }

    public void setPlannedDayMeals(Set<PlannedDayMealDto> plannedDayMealDtos) {
        this.plannedDayMealDtos = plannedDayMealDtos;
    }
}
