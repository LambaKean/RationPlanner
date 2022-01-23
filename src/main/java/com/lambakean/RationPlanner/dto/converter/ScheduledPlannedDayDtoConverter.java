package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.ScheduledPlannedDayDto;
import com.lambakean.RationPlanner.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduledPlannedDayDtoConverter {

    private final PlannedDayDtoConverter plannedDayDtoConverter;
    private final DateDtoConverter dateDtoConverter;

    @Autowired
    public ScheduledPlannedDayDtoConverter(PlannedDayDtoConverter plannedDayDtoConverter,
                                           DateDtoConverter dateDtoConverter) {
        this.plannedDayDtoConverter = plannedDayDtoConverter;
        this.dateDtoConverter = dateDtoConverter;
    }

    public ScheduledPlannedDayDto toScheduledPlannedDayDto(Schedule schedule, LocalDate date) {

        if(schedule == null) {
            return null;
        }

        ScheduledPlannedDayDto scheduledPlannedDayDto = new ScheduledPlannedDayDto();

        scheduledPlannedDayDto.setScheduleId(schedule.getId());
        scheduledPlannedDayDto.setDate(dateDtoConverter.toDateDto(date));
        scheduledPlannedDayDto.setPlannedDay(plannedDayDtoConverter.toPlannedDayDto(schedule.getPlannedDay()));

        return scheduledPlannedDayDto;
    }
}
