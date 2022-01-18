package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.dto.converter.PlannedDayDtoConverter;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.BadRequestException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.model.PlannedDayMeal;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.PlannedDayRepository;
import com.lambakean.RationPlanner.validator.PlannedDayValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.Collections;
import java.util.Optional;

@Component
public class PlannedDayServiceImpl implements PlannedDayService {

    private final PrincipalService principalService;
    private final PlannedDayRepository plannedDayRepository;
    private final PlannedDayDtoConverter plannedDayDtoConverter;
    private final PlannedDayValidator plannedDayValidator;
    private final PlannedDayMealService plannedDayMealService;

    public PlannedDayServiceImpl(PrincipalService principalService,
                                 PlannedDayRepository plannedDayRepository,
                                 PlannedDayDtoConverter plannedDayDtoConverter,
                                 PlannedDayValidator plannedDayValidator,
                                 PlannedDayMealService plannedDayMealService) {
        this.principalService = principalService;
        this.plannedDayRepository = plannedDayRepository;
        this.plannedDayDtoConverter = plannedDayDtoConverter;
        this.plannedDayValidator = plannedDayValidator;
        this.plannedDayMealService = plannedDayMealService;
    }

    @Override
    public PlannedDayDto createPlannedDay(PlannedDayDto plannedDayDto) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность создавать дни"
        );

        PlannedDay plannedDay = Optional.ofNullable(plannedDayDtoConverter.toPlannedDay(plannedDayDto)).orElseThrow(
                () -> new BadRequestException("Данные о создаваемом дне заполнены неверно")
        );

        plannedDay.setId(null);
        plannedDay.setSchedules(Collections.emptySet());

        plannedDay.getPlannedDayMeals()
                .stream()
                .peek(plannedDayMeal -> plannedDayMeal.setId(null))
                .forEach(plannedDayMealService::validate);

        validate(plannedDay);

        for(PlannedDayMeal plannedDayMeal : plannedDay.getPlannedDayMeals()) {
            Meal meal = plannedDayMeal.getMeal();

            if(meal != null && !meal.getUserId().equals(user.getId())) {
                throw new AccessDeniedException(
                        String.format("У вас нет доступа к блюду \"%s\"", meal.getName())
                );
            }
        }

        plannedDay.setUser(user);

        plannedDayRepository.saveAndFlush(plannedDay);

        return plannedDayDtoConverter.toPlannedDayDto(plannedDay);
    }

    @Override
    public PlannedDayDto getPlannedDayById(String id) {

        PlannedDay plannedDay = plannedDayRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("День с id \"%s\" не существует", id))
        );

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просмотреть информацию об этом дне"
        );

        if(!user.getId().equals(plannedDay.getUser().getId())) {
            throw new AccessDeniedException("Вы не имеете доступа к этому дню");
        }

        return plannedDayDtoConverter.toPlannedDayDto(plannedDay);
    }

    private void validate(PlannedDay plannedDay) {

        DataBinder dataBinder = new DataBinder(plannedDay, "plannedDay");
        dataBinder.addValidators(plannedDayValidator);

        dataBinder.validate();

        BindingResult productBindingResult = dataBinder.getBindingResult();
        if(productBindingResult.hasErrors()) {
            throw new InvalidEntityException(productBindingResult);
        }
    }
}
