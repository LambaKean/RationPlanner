package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.dto.ScheduledPlannedDayDto;

import java.time.LocalDate;
import java.util.Set;

public interface ScheduleService {

    ScheduleDto createSchedule(ScheduleDto scheduleDto);

    Set<ScheduledPlannedDayDto> getMonthSchedule(LocalDate date);
}
