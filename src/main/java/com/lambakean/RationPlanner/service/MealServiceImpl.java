package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.MealDto;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.exception.UserNotLoggedInException;
import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.MealRepository;
import com.lambakean.RationPlanner.validator.MealValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class MealServiceImpl implements MealService {

    private final MealValidator mealValidator;
    private final IngredientService ingredientService;
    private final PrincipalService principalService;
    private final MealRepository mealRepository;

    @Autowired
    public MealServiceImpl(MealValidator mealValidator,
                           IngredientService ingredientService,
                           PrincipalService principalService,
                           MealRepository mealRepository) {
        this.mealValidator = mealValidator;
        this.ingredientService = ingredientService;
        this.principalService = principalService;
        this.mealRepository = mealRepository;
    }

    @Override
    @Transactional
    public MealDto createMeal(@NonNull MealDto mealDto) {

        Meal meal = mealDto.toMeal();

        validate(meal);

        if(meal.getIngredients() != null) {
            meal.getIngredients().forEach(ingredientService::validate);
        }

        User user = (User) principalService.getPrincipal().orElseThrow(
                () -> new UserNotLoggedInException(
                        "Вы должны войти в аккаунт, чтобы добавлять рецепты ваших блюд"
                )
        );
        meal.setUser(user);

        mealRepository.saveAndFlush(meal);

        return MealDto.fromMeal(meal);
    }

    @Override
    public List<MealDto> getCurrentUserMeals() {

        User user = (User) principalService.getPrincipal().orElseThrow(
                () -> new UserNotLoggedInException(
                        "Вы должны войти в аккаунт, чтобы просматривать список своих блюд"
                )
        );

        List<Meal> meals = mealRepository.findAllByUser(user);

        return meals
                .stream()
                .map(MealDto::fromMeal)
                .toList();
    }

    @Override
    public MealDto getMealById(String id) {

        Meal meal = mealRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Блюдо с id [%s] не существует", id))
        );

        User user = (User) principalService.getPrincipal().orElseThrow(
                () -> new UserNotLoggedInException(
                        "Вы должны войти в аккаунт, чтобы просматривать список своих блюд"
                )
        );

        if(!user.getId().equals(meal.getUserId())) {
            throw new AccessDeniedException("Вы не имеете доступа к этому блюду.");
        }

        return MealDto.fromMeal(meal);
    }

    @Override
    public void deleteMealById(String id) {

        User user = (User) principalService.getPrincipal().orElseThrow(
                () -> new UserNotLoggedInException(
                        "Вы должны войти в аккаунт, чтобы иметь возможность удалять блюда"
                )
        );

        if(id == null || !mealRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Неверно указан идентификатор блюда, которое вы хотите удалить");
        }

        mealRepository.deleteById(id);
    }

    public void validate(Meal meal) {

        DataBinder dataBinder = new DataBinder(meal, "Meal");
        dataBinder.addValidators(mealValidator);

        dataBinder.validate();

        BindingResult mealBindingResult = dataBinder.getBindingResult();
        if(mealBindingResult.hasErrors()) {
            throw new InvalidEntityException(mealBindingResult);
        }
    }
}
