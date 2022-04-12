package com.lambakean.RationPlanner.domain.validator;

import com.lambakean.RationPlanner.data.model.Meal;
import com.lambakean.RationPlanner.data.model.Photo;
import com.lambakean.RationPlanner.data.model.Product;
import com.lambakean.RationPlanner.data.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MealValidator implements Validator {

    private final PhotoRepository photoRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Meal.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

        Meal meal = (Meal) target;

        validateName(meal.getName(), errors);
        validateDescription(meal.getDescription(), errors);
        validateCookingDuration(meal.getCookingDuration(), errors);
        validateRecipe(meal.getRecipe(), errors);
        validatePhoto(meal, errors);
    }

    public void validateName(String name, @NonNull Errors errors) {

        if (name == null) {
            errors.rejectValue(
                    "name",
                    "name.invalid",
                    "Название блюда не должно быть пустым"
            );
            return;
        }

        if(2 > name.length() || name.length() > 50) {
            errors.rejectValue(
                    "name",
                    "name.invalid",
                    "Название блюда должно иметь длину от 2 до 50 символов"
            );
        }
    }

    public void validateDescription(String description, @NonNull Errors errors) {

        if(description == null) {
            return;
        }

        if(1 > description.length() || description.length() > 250) {
            errors.rejectValue(
                    "description",
                    "description.invalid",
                    "Описание блюда должно иметь длину от 1 до 255 символов"
            );
        }
    }

    public void validateCookingDuration(Duration duration, @NonNull Errors errors) {
        if(duration == null) {
            errors.rejectValue(
                    "cookingDuration",
                    "cookingDuration.invalid",
                    "Время приготовления блюда не указано или указано неверно"
            );
        }
    }

    public void validateRecipe(String recipe, @NonNull Errors errors) {
        if(recipe == null || recipe.equals("")) {
            return;
        }

        if(1 > recipe.length() || recipe.length() > 10000) {
            errors.rejectValue(
                    "recipe",
                    "recipe.invalid",
                    "Описание рецепта блюда не должно быть длиннее 10000 символов"
            );
        }
    }

    public void validatePhoto(@NonNull Meal meal, @NonNull Errors errors) {

        Photo photo = meal.getPhoto();

        if(photo == null) return;

        if(photo.getId() == null) {
            errors.rejectValue(
                    "photo",
                    "photo.invalid",
                    "Данные о фотографии блюда заполнены неверно"
            );
        }

        if(!photoRepository.existsById(photo.getId())) {
            errors.rejectValue(
                    "photo",
                    "photo.invalid",
                    String.format("Фотография с id [%s] не существует", photo.getId())
            );
        }
    }
}
