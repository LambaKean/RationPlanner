package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.DateDto;
import com.lambakean.RationPlanner.dto.ScheduledPlannedDayDto;
import com.lambakean.RationPlanner.dto.TimeDto;
import com.lambakean.RationPlanner.model.ScheduledPlannedDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public abstract class ScheduledPlannedDayMapper {

    @Autowired
    protected DateAndTimeMapper dateAndTimeMapper;

    @Autowired
    protected PlannedDayMapper plannedDayMapper;

    @Mapping(
            target = "plannedDay",
            expression = "java(plannedDayMapper.toPlannedDayDto(scheduledPlannedDay.getPlannedDay()))"
    )
    public abstract ScheduledPlannedDayDto toScheduledPlannedDayDto(ScheduledPlannedDay scheduledPlannedDay);

    protected LocalDate map(DateDto dateDto) {
        return dateAndTimeMapper.toLocalDate(dateDto);
    }

    protected DateDto map(LocalDate localDate) {
        return dateAndTimeMapper.toDateDto(localDate);
    }
}
