package com.lambakean.rationplanner.domain.service;

import com.lambakean.rationplanner.data.model.Schedule;
import com.lambakean.rationplanner.data.model.ScheduledPlannedDay;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule scheduleData);

    List<ScheduledPlannedDay> getMonthSchedule(LocalDate date);

    Schedule getScheduleById(String id);

    void deleteScheduleById(String id);
}
