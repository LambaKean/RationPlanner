package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.PlannedDayRepository;
import com.lambakean.RationPlanner.service.MealService;
import com.lambakean.RationPlanner.service.PlannedDayService;
import com.lambakean.RationPlanner.service.PrincipalService;
import com.lambakean.RationPlanner.service.ValidationService;
import com.lambakean.RationPlanner.validator.PlannedDayMealValidator;
import com.lambakean.RationPlanner.validator.PlannedDayValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlannedDayServiceImpl implements PlannedDayService {

    private final PrincipalService principalService;
    private final PlannedDayRepository plannedDayRepository;
    private final PlannedDayValidator plannedDayValidator;
    private final ValidationService validationService;
    private final PlannedDayMealValidator plannedDayMealValidator;
    private final MealService mealService;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public PlannedDay createPlannedDay(PlannedDay plannedDayData) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность создавать дни"
        );

        plannedDayData.setUser(user);

        plannedDayData.getPlannedDayMeals()
                .forEach(plannedDayMeal -> {

                    plannedDayMeal.setPlannedDay(plannedDayData);

                    validationService.validateThrowExceptionIfInvalid(plannedDayMeal, plannedDayMealValidator);

                    Meal meal = plannedDayMeal.getMeal();

                    if(!mealService.belongsTo(meal.getId(), user.getId())) {
                        throw new AccessDeniedException(
                                String.format("У вас нет доступа к блюду c id [%s]", meal.getName())
                        );
                    }
                });

        validationService.validateThrowExceptionIfInvalid(plannedDayData, plannedDayValidator);

        plannedDayRepository.saveAndFlush(plannedDayData);

        entityManager.clear();

        return plannedDayRepository.findById(plannedDayData.getId()).get();
    }

    @Override
    public PlannedDay getPlannedDayById(@NonNull String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просмотреть информацию об этом дне"
        );

        PlannedDay plannedDay = plannedDayRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("День с id [%s] не существует", id))
        );

        if(!user.getId().equals(plannedDay.getUser().getId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступа ко дню с id [%s]", id));
        }

        return plannedDay;
    }

    @Override
    @Transactional
    public Set<PlannedDay> getCurrentUserPlannedDays() {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просмотреть список своих дней"
        );

        return plannedDayRepository.findByUser(user);
    }

    @Override
    public void deletePlannedDayById(@NonNull String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность удалять дни"
        );

        PlannedDay plannedDay = plannedDayRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("День с id [%s] не существует")
        );

        if(!plannedDay.getUserId().equals(user.getId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступ ко дню с id [%s]", id));
        }

        plannedDayRepository.delete(plannedDay);
    }
}