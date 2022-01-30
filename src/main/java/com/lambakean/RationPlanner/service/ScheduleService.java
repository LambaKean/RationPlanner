package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.dto.ScheduledPlannedDayDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleDto createSchedule(ScheduleDto scheduleDto);

    List<ScheduledPlannedDayDto> getMonthSchedule(LocalDate date);

    ScheduleDto getScheduleById(String id);

    void deleteScheduleById(String id);
}
