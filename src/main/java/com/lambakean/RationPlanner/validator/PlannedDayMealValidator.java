package com.lambakean.RationPlanner.validator;

import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.model.PlannedDayMeal;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalTime;

@Component
public class PlannedDayMealValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return PlannedDayMeal.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

        PlannedDayMeal plannedDayMeal = (PlannedDayMeal) target;

        validatePlannedDay(plannedDayMeal.getPlannedDay(), errors);
        validateMeal(plannedDayMeal.getMeal(), errors);
        validateTime(plannedDayMeal.getTime(), errors);
    }

    public void validatePlannedDay(PlannedDay plannedDay, @NonNull Errors errors) {
        if(plannedDay == null) {
            errors.rejectValue(
                    "plannedDay",
                    "plannedDay.empty",
                    "День, к которому нужно привязать выбранное блюдо, указан неверно"
            );
        }
    }

    public void validateMeal(Meal meal, @NonNull Errors errors) {

        if(meal == null) {
            errors.rejectValue(
                    "meal",
                    "meal.empty",
                    "Блюдо, которое нужно добавить ко дню, не выбрано или выбрано неверно"
            );
        }
    }

    public void validateTime(LocalTime time, @NonNull Errors errors) {

        if(time == null) {
            errors.rejectValue(
                    "time",
                    "time.empty",
                    "Время начала приготовления блюда не выбрано или невалидно"
            );
        }
    }
}
