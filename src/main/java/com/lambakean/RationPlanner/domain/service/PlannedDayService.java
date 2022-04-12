package com.lambakean.RationPlanner.domain.service;

import com.lambakean.RationPlanner.data.model.PlannedDay;

import java.util.Set;

public interface PlannedDayService {

    PlannedDay createPlannedDay(PlannedDay plannedDayData);

    PlannedDay getPlannedDayById(String id);

    Set<PlannedDay> getCurrentUserPlannedDays();

    void deletePlannedDayById(String id);
}
