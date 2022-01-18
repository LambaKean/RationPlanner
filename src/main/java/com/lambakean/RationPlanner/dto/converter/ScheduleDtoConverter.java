package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDtoConverter {

    private final PlannedDayDtoConverter plannedDayDtoConverter;
    private final DateDtoConverter dateDtoConverter;

    @Autowired
    public ScheduleDtoConverter(PlannedDayDtoConverter plannedDayDtoConverter,
                                DateDtoConverter dateDtoConverter) {
        this.plannedDayDtoConverter = plannedDayDtoConverter;
        this.dateDtoConverter = dateDtoConverter;
    }

    public Schedule toSchedule(ScheduleDto scheduleDto) {

        if(scheduleDto == null) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setId(scheduleDto.getId());
        schedule.setPlannedDay(plannedDayDtoConverter.toPlannedDay(scheduleDto.getPlannedDay()));
        schedule.setStartDate(dateDtoConverter.toLocalDate(scheduleDto.getStartDate()));
        schedule.setNextRepeatAfterDays(scheduleDto.getNextRepeatAfterDays());

        return schedule;
    }

    public ScheduleDto toScheduleDto(Schedule schedule) {

        if(schedule == null) {
            return null;
        }

        ScheduleDto scheduleDto = new ScheduleDto();

        scheduleDto.setId(schedule.getId());
        scheduleDto.setPlannedDay(plannedDayDtoConverter.toPlannedDayDto(schedule.getPlannedDay()));
        scheduleDto.setStartDate(dateDtoConverter.toDateDto(schedule.getStartDate()));
        scheduleDto.setNextRepeatAfterDays(schedule.getNextRepeatAfterDays());

        return scheduleDto;
    }
}
