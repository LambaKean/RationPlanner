package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.PlannedDayDto;

import java.util.Set;

public interface PlannedDayService {

    PlannedDayDto createPlannedDay(PlannedDayDto plannedDayDto);

    PlannedDayDto getPlannedDayById(String id);

    Set<PlannedDayDto> getCurrentUserPlannedDays();

    void deletePlannedDayById(String id);
}
