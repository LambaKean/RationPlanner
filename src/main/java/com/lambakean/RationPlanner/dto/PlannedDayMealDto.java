package com.lambakean.RationPlanner.dto;

public class PlannedDayMealDto {

    private String id;
    private EntityIdReferenceDto meal;
    private TimeDto time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityIdReferenceDto getMeal() {
        return meal;
    }

    public void setMeal(EntityIdReferenceDto meal) {
        this.meal = meal;
    }

    public TimeDto getTime() {
        return time;
    }

    public void setTime(TimeDto time) {
        this.time = time;
    }
}
