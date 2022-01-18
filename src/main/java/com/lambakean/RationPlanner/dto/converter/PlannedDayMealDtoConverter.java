package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.PlannedDayMealDto;
import com.lambakean.RationPlanner.model.PlannedDayMeal;
import com.lambakean.RationPlanner.repository.MealRepository;
import com.lambakean.RationPlanner.repository.PlannedDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlannedDayMealDtoConverter {

    private final PlannedDayRepository plannedDayRepository;
    private final MealRepository mealRepository;
    private final TimeDtoConverter timeDtoConverter;

    @Autowired
    public PlannedDayMealDtoConverter(PlannedDayRepository plannedDayRepository,
                                      MealRepository mealRepository,
                                      TimeDtoConverter timeDtoConverter) {
        this.plannedDayRepository = plannedDayRepository;
        this.mealRepository = mealRepository;
        this.timeDtoConverter = timeDtoConverter;
    }

    public PlannedDayMeal toPlannedDayMeal(PlannedDayMealDto plannedDayMealDto) {

        if(plannedDayMealDto == null) {
            return null;
        }

        PlannedDayMeal plannedDayMeal = new PlannedDayMeal();

        plannedDayMeal.setId(plannedDayMealDto.getId());

        if(plannedDayMealDto.getPlannedDayId() != null) {
            plannedDayMeal.setPlannedDay(
                    plannedDayRepository.findById(plannedDayMealDto.getPlannedDayId()).orElse(null)
            );
        }

        if(plannedDayMealDto.getMealId() != null) {
            plannedDayMeal.setMeal(
                    mealRepository.findById(plannedDayMealDto.getMealId()).orElse(null)
            );
        }

        plannedDayMeal.setTime(timeDtoConverter.toLocalTime(plannedDayMealDto.getTime()));

        return plannedDayMeal;
    }

    public PlannedDayMealDto toPlannedDayMealDto(PlannedDayMeal plannedDayMeal) {

        if(plannedDayMeal == null) {
            return null;
        }

        PlannedDayMealDto plannedDayMealDto = new PlannedDayMealDto();

        plannedDayMealDto.setId(plannedDayMeal.getId());

        if(plannedDayMeal.getPlannedDay() != null) {
            plannedDayMealDto.setPlannedDayId(
                    plannedDayMeal.getPlannedDay().getId()
            );
        }

        if(plannedDayMeal.getMeal() != null) {
            plannedDayMealDto.setMealId(
                    plannedDayMeal.getMeal().getId()
            );
        }

        if(plannedDayMeal.getTime() != null) {
            plannedDayMealDto.setTime(
                    timeDtoConverter.toTimeDto(plannedDayMeal.getTime())
            );
        }

        return plannedDayMealDto;
    }
}
