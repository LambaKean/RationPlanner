package com.lambakean.RationPlanner.domain.mapper;

import com.lambakean.RationPlanner.data.model.Schedule;
import com.lambakean.RationPlanner.representation.dto.DateDto;
import com.lambakean.RationPlanner.representation.dto.ScheduleDto;
import com.lambakean.RationPlanner.representation.dto.TimeDto;
import com.lambakean.RationPlanner.representation.dto.form.ScheduleCreationForm;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public abstract class ScheduleMapper {

    @Autowired
    protected DateAndTimeMapper dateAndTimeMapper;

    public abstract Schedule toSchedule(ScheduleCreationForm scheduleCreationForm);

    public abstract ScheduleDto toScheduleDto(Schedule schedule);


    protected LocalDate map(DateDto dateDto) {
        return dateAndTimeMapper.toLocalDate(dateDto);
    }

    protected DateDto map(LocalDate localDate) {
        return dateAndTimeMapper.toDateDto(localDate);
    }

    protected LocalTime map(TimeDto timeDto) {
        return dateAndTimeMapper.toLocalTime(timeDto);
    }

    protected TimeDto map(LocalTime localTime) {
        return dateAndTimeMapper.toTimeDto(localTime);
    }
}
