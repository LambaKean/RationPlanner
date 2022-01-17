package com.lambakean.RationPlanner.validator;

import com.lambakean.RationPlanner.model.Ingredient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IngredientValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Ingredient.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

        Ingredient ingredient = (Ingredient) target;

        validateName(ingredient.getName(), errors);
    }

    public void validateName(String name, @NonNull Errors errors) {

        if (name == null) {
            errors.rejectValue(
                    "name",
                    "name.invalid",
                    "Название ингредиента не должно быть пустым"
            );
            return;
        }

        if(2 > name.length() || name.length() > 50) {
            errors.rejectValue(
                    "name",
                    "name.invalid",
                    "Название ингредиента должно иметь длину от 2 до 50 символов"
            );
        }
    }
}
