package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.repository.PlannedDayRepository;
import com.lambakean.RationPlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class PlannedDayDtoConverter {

    private final UserRepository userRepository;
    private final PlannedDayMealDtoConverter plannedDayMealDtoConverter;
    private final PlannedDayRepository plannedDayRepository;

    @Autowired
    public PlannedDayDtoConverter(UserRepository userRepository,
                                  PlannedDayMealDtoConverter plannedDayMealDtoConverter,
                                  PlannedDayRepository plannedDayRepository) {
        this.userRepository = userRepository;
        this.plannedDayMealDtoConverter = plannedDayMealDtoConverter;
        this.plannedDayRepository = plannedDayRepository;
    }

    public PlannedDay toPlannedDay(PlannedDayDto plannedDayDto) {

        if(plannedDayDto == null) {
            return null;
        }

        if(plannedDayDto.getId() != null) {
            return plannedDayRepository.findById(plannedDayDto.getId()).orElse(null);
        }

        PlannedDay plannedDay = new PlannedDay();

        plannedDay.setId(plannedDayDto.getId());
        plannedDay.setName(plannedDayDto.getName());

        if(plannedDayDto.getUserId() != null) {
            plannedDay.setUser(
                    userRepository.findById(plannedDayDto.getUserId()).orElse(null)
            );
        }

        if(plannedDayDto.getPlannedDayMeals() != null) {
            plannedDay.setPlannedDayMeals(
                    plannedDayDto.getPlannedDayMeals()
                            .stream()
                            .map(plannedDayMealDtoConverter::toPlannedDayMeal)
                            .peek(plannedDayMeal -> plannedDayMeal.setPlannedDay(plannedDay))
                            .collect(Collectors.toSet())
            );
        } else {
            plannedDay.setPlannedDayMeals(new HashSet<>());
        }

        return plannedDay;
    }

    public PlannedDayDto toPlannedDayDto(PlannedDay plannedDay) {

        if(plannedDay == null) {
            return null;
        }

        PlannedDayDto plannedDayDto = new PlannedDayDto();

        plannedDayDto.setId(plannedDay.getId());
        plannedDayDto.setName(plannedDay.getName());

        if(plannedDay.getUser() != null) {
            plannedDayDto.setUserId(plannedDay.getUser().getId());
        }

        plannedDayDto.setPrice(plannedDay.getPrice());
        plannedDayDto.setAmountOfMeals(plannedDay.getAmountOfMeals());

        plannedDayDto.setPlannedDayMeals(
                plannedDay.getPlannedDayMeals()
                        .stream()
                        .map(plannedDayMealDtoConverter::toPlannedDayMealDto)
                        .collect(Collectors.toSet())
        );

        return plannedDayDto;
    }
}
