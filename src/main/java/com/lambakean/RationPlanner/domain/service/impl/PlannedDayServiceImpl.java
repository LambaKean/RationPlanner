package com.lambakean.RationPlanner.domain.service.impl;

import com.lambakean.RationPlanner.data.model.Meal;
import com.lambakean.RationPlanner.data.model.PlannedDay;
import com.lambakean.RationPlanner.data.model.User;
import com.lambakean.RationPlanner.data.repository.PlannedDayRepository;
import com.lambakean.RationPlanner.domain.exception.AccessDeniedException;
import com.lambakean.RationPlanner.domain.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.domain.service.MealService;
import com.lambakean.RationPlanner.domain.service.PlannedDayService;
import com.lambakean.RationPlanner.domain.service.PrincipalService;
import com.lambakean.RationPlanner.domain.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlannedDayServiceImpl implements PlannedDayService {

    private final PrincipalService principalService;
    private final PlannedDayRepository plannedDayRepository;
    private final MealService mealService;
    private final EntityManager entityManager;

    private final ValidationService validationService;

    private final Validator plannedDayValidator;
    private final Validator plannedDayMealValidator;



    @Override
    @Transactional
    public PlannedDay createPlannedDay(PlannedDay plannedDayData) {

        User user = principalService.getPrincipal();

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

        User user = principalService.getPrincipal();

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

        User user = principalService.getPrincipal();

        return plannedDayRepository.findByUser(user);
    }

    @Override
    public void deletePlannedDayById(@NonNull String id) {

        User user = principalService.getPrincipal();

        PlannedDay plannedDay = plannedDayRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("День с id [%s] не существует")
        );

        if(!plannedDay.getUserId().equals(user.getId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступ ко дню с id [%s]", id));
        }

        plannedDayRepository.delete(plannedDay);
    }
}