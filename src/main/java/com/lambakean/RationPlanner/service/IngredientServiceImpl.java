package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.model.Ingredient;
import com.lambakean.RationPlanner.validator.IngredientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

@Component
public class IngredientServiceImpl implements IngredientService {

    private final IngredientValidator ingredientValidator;

    @Autowired
    public IngredientServiceImpl(IngredientValidator ingredientValidator) {
        this.ingredientValidator = ingredientValidator;
    }

    @Override
    public void validate(@NonNull Ingredient ingredient) {

        DataBinder dataBinder = new DataBinder(ingredient);

        dataBinder.addValidators(ingredientValidator);
        dataBinder.validate();

        BindingResult ingredientBindingResult = dataBinder.getBindingResult();
        if(ingredientBindingResult.hasErrors()) {
            throw new InvalidEntityException(ingredientBindingResult);
        }
    }
}
