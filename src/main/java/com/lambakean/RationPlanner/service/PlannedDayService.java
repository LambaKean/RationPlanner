package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.PlannedDayDto;
import com.lambakean.RationPlanner.model.PlannedDay;

import java.util.Set;

public interface PlannedDayService {

    PlannedDay createPlannedDay(PlannedDay plannedDayData);

    PlannedDay getPlannedDayById(String id);

    Set<PlannedDay> getCurrentUserPlannedDays();

    void deletePlannedDayById(String id);
}
