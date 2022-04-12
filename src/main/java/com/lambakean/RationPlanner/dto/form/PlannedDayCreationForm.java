package com.lambakean.RationPlanner.dto.form;

import com.lambakean.RationPlanner.dto.EntityIdReferenceDto;
import com.lambakean.RationPlanner.dto.TimeDto;

import java.util.List;

public class PlannedDayCreationForm {

    private String name;
    private List<PlannedDayMealInformation> plannedDayMeals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlannedDayMealInformation> getPlannedDayMeals() {
        return plannedDayMeals;
    }

    public void setPlannedDayMeals(List<PlannedDayMealInformation> plannedDayMeals) {
        this.plannedDayMeals = plannedDayMeals;
    }

    public static class PlannedDayMealInformation {

        private EntityIdReferenceDto meal;
        private TimeDto time;

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
}
