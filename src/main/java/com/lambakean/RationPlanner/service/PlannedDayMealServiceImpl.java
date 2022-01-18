package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.model.PlannedDayMeal;
import com.lambakean.RationPlanner.validator.PlannedDayMealValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

@Component
public class PlannedDayMealServiceImpl implements PlannedDayMealService {

    private final PlannedDayMealValidator plannedDayMealValidator;

    @Autowired
    public PlannedDayMealServiceImpl(PlannedDayMealValidator plannedDayMealValidator) {
        this.plannedDayMealValidator = plannedDayMealValidator;
    }

    @Override
    public void validate(PlannedDayMeal plannedDayMeal) {

        DataBinder dataBinder = new DataBinder(plannedDayMeal, "plannedDayMeal");
        dataBinder.addValidators(plannedDayMealValidator);

        dataBinder.validate();

        BindingResult productBindingResult = dataBinder.getBindingResult();
        if(productBindingResult.hasErrors()) {
            throw new InvalidEntityException(productBindingResult);
        }
    }
}
