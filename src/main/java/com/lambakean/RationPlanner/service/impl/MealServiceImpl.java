package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.MealRepository;
import com.lambakean.RationPlanner.service.MealService;
import com.lambakean.RationPlanner.service.PrincipalService;
import com.lambakean.RationPlanner.service.ValidationService;
import com.lambakean.RationPlanner.validator.MealValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final PrincipalService principalService;

    private final MealRepository mealRepository;
    private final EntityManager entityManager;

    private final ValidationService validationService;
    private final MealValidator mealValidator;
    private final Validator ingredientValidator;

    @Autowired
    public MealServiceImpl(MealValidator mealValidator,
                           PrincipalService principalService,
                           ValidationService validationService,
                           MealRepository mealRepository,
                           EntityManager entityManager, Validator ingredientValidator) {
        this.mealValidator = mealValidator;
        this.principalService = principalService;
        this.validationService = validationService;
        this.mealRepository = mealRepository;
        this.entityManager = entityManager;
        this.ingredientValidator = ingredientValidator;
    }

    @Override
    @Transactional
    public Meal createMeal(@NonNull Meal mealData) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы добавлять рецепты ваших блюд"
        );
        mealData.setUser(user);

        if(mealData.getIngredients() == null) {
            mealData.setIngredients(new ArrayList<>());
        }
        mealData.getIngredients()
                .forEach(ingredient -> {
                    ingredient.setMeal(mealData);
                    validationService.validateThrowExceptionIfInvalid(ingredient, ingredientValidator);
                });

        validationService.validateThrowExceptionIfInvalid(mealData, mealValidator);

        mealRepository.saveAndFlush(mealData);

        entityManager.clear();

        return mealRepository.getById(mealData.getId());
    }

    @Override
    public Meal getMealById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность просматривать список своих блюд"
        );

        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Блюдо с id [%s] не существует", id))
        );

        if(!user.getId().equals(meal.getUserId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступа к блюду с id [%s].", id));
        }

        return meal;
    }

    @Override
    public void deleteMealById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность удалять блюда"
        );

        if(id == null || !mealRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException(String.format("Блюдо с id [%s] не существует", id));
        }

        mealRepository.deleteById(id);
    }

    @Override
    public List<Meal> getCurrentUserMeals() {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность просматривать список своих блюд"
        );

        return mealRepository.findAllByUser(user);
    }

    @Override
    public boolean belongsTo(@NonNull String mealId, @NonNull String userId) {

        Meal meal = getMealById(mealId);

        return meal.getUserId().equals(userId);
    }
}
