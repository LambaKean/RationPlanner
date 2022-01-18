package com.lambakean.RationPlanner.dto;

public class PlannedDayMealDto {

    private String id;
    private String plannedDayId;
    private String mealId;
    private DurationDto time;

    public PlannedDayMealDto(String id, String plannedDayId, String mealId, DurationDto time) {
        this.id = id;
        this.plannedDayId = plannedDayId;
        this.mealId = mealId;
        this.time = time;
    }

    public PlannedDayMealDto() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlannedDayId() {
        return plannedDayId;
    }

    public void setPlannedDayId(String plannedDayId) {
        this.plannedDayId = plannedDayId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public DurationDto getTime() {
        return time;
    }

    public void setTime(DurationDto time) {
        this.time = time;
    }
}
