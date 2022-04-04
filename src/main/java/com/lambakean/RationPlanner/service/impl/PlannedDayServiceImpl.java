package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.dto.converter.PlannedDayDtoConverter;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.BadRequestException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.Meal;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.model.PlannedDayMeal;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.PlannedDayRepository;
import com.lambakean.RationPlanner.service.PlannedDayService;
import com.lambakean.RationPlanner.service.PrincipalService;
import com.lambakean.RationPlanner.service.ValidationService;
import com.lambakean.RationPlanner.validator.PlannedDayMealValidator;
import com.lambakean.RationPlanner.validator.PlannedDayValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlannedDayServiceImpl implements PlannedDayService {

    private final PrincipalService principalService;
    private final PlannedDayRepository plannedDayRepository;
    private final PlannedDayDtoConverter plannedDayDtoConverter;
    private final PlannedDayValidator plannedDayValidator;
    private final ValidationService validationService;
    private final PlannedDayMealValidator plannedDayMealValidator;

    public PlannedDayServiceImpl(PrincipalService principalService,
                                 PlannedDayRepository plannedDayRepository,
                                 PlannedDayDtoConverter plannedDayDtoConverter,
                                 PlannedDayValidator plannedDayValidator,
                                 ValidationService validationService,
                                 PlannedDayMealValidator plannedDayMealValidator) {
        this.principalService = principalService;
        this.plannedDayRepository = plannedDayRepository;
        this.plannedDayDtoConverter = plannedDayDtoConverter;
        this.plannedDayValidator = plannedDayValidator;
        this.validationService = validationService;
        this.plannedDayMealValidator = plannedDayMealValidator;
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
                .forEach(plannedDayMeal ->
                        validationService.throwExceptionIfObjectIsInvalid(
                                plannedDayMeal,
                                "plannedDayMeal",
                                plannedDayMealValidator
                        )
                );

        validationService.throwExceptionIfObjectIsInvalid(plannedDay, "plannedDay", plannedDayValidator);

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
    @Transactional
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

    @Override
    @Transactional
    public Set<PlannedDayDto> getCurrentUserPlannedDays() {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просмотреть список своих дней"
        );

        Set<PlannedDay> currentUserPlannedDays = plannedDayRepository.findByUser(user);

        return currentUserPlannedDays
                .stream()
                .map(plannedDayDtoConverter::toPlannedDayDto)
                .collect(Collectors.toSet());
    }

    public void deletePlannedDayById(String id) {
        User user = (User) principalService.getPrincipalOrElseThrowException(
            "Вы должны войти в аккаунт, чтобы иметь возможность удалять дни"
        );

        if(id == null || !plannedDayRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Неверно указан идентификатор дня, который вы хотите удалить");
        }

        plannedDayRepository.deleteById(id);
    }
}