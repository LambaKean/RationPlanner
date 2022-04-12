package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.DateDto;
import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.dto.TimeDto;
import com.lambakean.RationPlanner.dto.form.ScheduleCreationForm;
import com.lambakean.RationPlanner.model.Schedule;
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
