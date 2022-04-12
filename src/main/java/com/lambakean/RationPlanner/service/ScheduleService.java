package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.model.Schedule;
import com.lambakean.RationPlanner.model.ScheduledPlannedDay;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule scheduleData);

    List<ScheduledPlannedDay> getMonthSchedule(LocalDate date);

    Schedule getScheduleById(String id);

    void deleteScheduleById(String id);
}
