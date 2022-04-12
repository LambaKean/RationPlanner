package com.lambakean.RationPlanner.dto.form;

import com.lambakean.RationPlanner.dto.EntityIdReferenceDto;
import com.lambakean.RationPlanner.dto.TimeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlannedDayCreationForm {

    private String name;
    private List<PlannedDayMealInformation> plannedDayMeals;

    @Getter
    @Setter
    public static class PlannedDayMealInformation {

        private EntityIdReferenceDto meal;
        private TimeDto time;
    }
}
