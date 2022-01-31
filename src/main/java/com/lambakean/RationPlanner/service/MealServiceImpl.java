package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.MealDto;
import com.lambakean.RationPlanner.dto.converter.MealDtoConverter;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.BadRequestException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.MealRepository;
import com.lambakean.RationPlanner.validator.IngredientValidator;
import com.lambakean.RationPlanner.validator.MealValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class MealServiceImpl implements MealService {

    private final MealValidator mealValidator;
    private final PrincipalService principalService;
    private final MealRepository mealRepository;
    private final MealDtoConverter mealDtoConverter;
    private final ValidationService validationService;
    private final IngredientValidator ingredientValidator;

    @Autowired
    public MealServiceImpl(MealValidator mealValidator,
                           PrincipalService principalService,
                           MealRepository mealRepository,
                           MealDtoConverter mealDtoConverter,
                           ValidationService validationService,
                           IngredientValidator ingredientValidator) {
        this.mealValidator = mealValidator;
        this.principalService = principalService;
        this.mealRepository = mealRepository;
        this.mealDtoConverter = mealDtoConverter;
        this.validationService = validationService;
        this.ingredientValidator = ingredientValidator;
    }

    @Override
    @Transactional
    public MealDto createMeal(MealDto mealDto) {

        Meal meal = mealDtoConverter.toMeal(mealDto);

        if(meal == null) {
            throw new BadRequestException("Данные для создания блюда указаны неверно");
        }

        validationService.throwExceptionIfObjectIsInvalid(meal, "meal", mealValidator);

        if(meal.getIngredients() != null) {
            meal.getIngredients()
                    .stream()
                    .peek(ingredient -> ingredient.setMeal(meal))
                    .forEach(
                            ingredient ->
                                    validationService.throwExceptionIfObjectIsInvalid(
                                            ingredient,
                                            "ingredient",
                                            ingredientValidator
                                    )
                    );
        }

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы добавлять рецепты ваших блюд"
        );

        meal.setUser(user);

        mealRepository.saveAndFlush(meal);

        return mealDtoConverter.toMealDto(meal);
    }

    @Override
    @Transactional
    public List<MealDto> getCurrentUserMeals() {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просматривать список своих блюд"
        );

        List<Meal> meals = mealRepository.findAllByUser(user);

        return meals
                .stream()
                .map(mealDtoConverter::toMealDto)
                .toList();
    }

    @Override
    public MealDto getMealById(String id) {

        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Блюдо с id [%s] не существует", id))
        );

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просматривать список своих блюд"
        );

        if(!user.getId().equals(meal.getUserId())) {
            throw new AccessDeniedException("Вы не имеете доступа к этому блюду.");
        }

        return mealDtoConverter.toMealDto(meal);
    }

    @Override
    public void deleteMealById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность удалять блюда"
        );

        if(id == null || !mealRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Неверно указан идентификатор блюда, которое вы хотите удалить");
        }

        mealRepository.deleteById(id);
    }
}
