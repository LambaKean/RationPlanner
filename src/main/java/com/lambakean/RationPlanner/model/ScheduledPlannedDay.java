package com.lambakean.RationPlanner.model;

import java.time.LocalDate;

public class ScheduledPlannedDay {

    private Schedule schedule;
    private PlannedDay plannedDay;
    private LocalDate date;

    public ScheduledPlannedDay(Schedule schedule, PlannedDay plannedDay, LocalDate date) {
        this.schedule = schedule;
        this.plannedDay = plannedDay;
        this.date = date;
    }

    public ScheduledPlannedDay() {}


    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public PlannedDay getPlannedDay() {
        return plannedDay;
    }

    public void setPlannedDay(PlannedDay plannedDay) {
        this.plannedDay = plannedDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}