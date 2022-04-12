package com.lambakean.RationPlanner.domain.service;

import com.lambakean.RationPlanner.data.model.Schedule;
import com.lambakean.RationPlanner.data.model.ScheduledPlannedDay;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule scheduleData);

    List<ScheduledPlannedDay> getMonthSchedule(LocalDate date);

    Schedule getScheduleById(String id);

    void deleteScheduleById(String id);
}
