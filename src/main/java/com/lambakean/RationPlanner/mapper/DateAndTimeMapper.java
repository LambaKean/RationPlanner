package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.DateDto;
import com.lambakean.RationPlanner.dto.TimeDto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public interface DateAndTimeMapper {

    Duration toDuration(TimeDto timeDto);

    TimeDto toTimeDto(Duration duration);

    LocalTime toLocalTime(TimeDto timeDto);

    TimeDto toTimeDto(LocalTime localTime);

    LocalDate toLocalDate(DateDto dateDto);

    DateDto toDateDto(LocalDate localDate);
}
