package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.PlannedDayDto;

public interface PlannedDayService {

    PlannedDayDto createPlannedDay(PlannedDayDto plannedDayDto);

    PlannedDayDto getPlannedDayById(String id);
}
