package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.model.Ingredient;
import org.springframework.lang.NonNull;

public interface IngredientService {

    void validate(@NonNull Ingredient ingredient);
}
