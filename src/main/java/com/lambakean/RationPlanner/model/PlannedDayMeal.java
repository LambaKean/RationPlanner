package com.lambakean.RationPlanner.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "planned_day_meals")
public class PlannedDayMeal extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "planned_day_id", nullable = false)
    private PlannedDay plannedDay;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    private LocalTime time;

    public PlannedDayMeal(PlannedDay plannedDay, Meal meal, LocalTime time) {
        this.plannedDay = plannedDay;
        this.meal = meal;
        this.time = time;
    }

    public PlannedDayMeal() {}

    public Double getPrice() {
        if(meal == null) {
            return 0.0;
        }

        return meal.getPrice();
    }


    public PlannedDay getPlannedDay() {
        return plannedDay;
    }

    public void setPlannedDay(PlannedDay plannedDay) {
        this.plannedDay = plannedDay;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
