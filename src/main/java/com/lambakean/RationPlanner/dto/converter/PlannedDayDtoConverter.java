package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlannedDayDtoConverter {

    private final UserRepository userRepository;
    private final PlannedDayMealDtoConverter plannedDayMealDtoConverter;

    @Autowired
    public PlannedDayDtoConverter(UserRepository userRepository,
                                  PlannedDayMealDtoConverter plannedDayMealDtoConverter) {
        this.userRepository = userRepository;
        this.plannedDayMealDtoConverter = plannedDayMealDtoConverter;
    }

    public PlannedDay toPlannedDay(PlannedDayDto plannedDayDto) {

        if(plannedDayDto == null) {
            return null;
        }

        PlannedDay plannedDay = new PlannedDay();

        plannedDay.setId(plannedDayDto.getId());
        plannedDay.setName(plannedDayDto.getName());

        if(plannedDayDto.getUserId() != null) {
            plannedDay.setUser(
                    userRepository.findById(plannedDayDto.getUserId()).orElse(null)
            );
        }

        plannedDay.setPlannedDayMeals(
                plannedDayDto.getPlannedDayMeals()
                        .stream()
                        .map(plannedDayMealDtoConverter::toPlannedDayMeal)
                        .peek(plannedDayMeal -> plannedDayMeal.setPlannedDay(plannedDay))
                        .collect(Collectors.toSet())
        );

        return plannedDay;
    }

    public PlannedDayDto toPlannedDayDto(PlannedDay plannedDay) {

        PlannedDayDto plannedDayDto = new PlannedDayDto();

        plannedDayDto.setId(plannedDay.getId());
        plannedDayDto.setName(plannedDay.getName());

        if(plannedDay.getUser() != null) {
            plannedDayDto.setUserId(plannedDay.getUser().getId());
        }

        plannedDayDto.setPlannedDayMeals(
                plannedDay.getPlannedDayMeals()
                        .stream()
                        .map(plannedDayMealDtoConverter::toPlannedDayMealDto)
                        .collect(Collectors.toSet())
        );

        return plannedDayDto;
    }
}
