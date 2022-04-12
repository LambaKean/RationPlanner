package com.lambakean.RationPlanner.dto;

public class ScheduledPlannedDayDto {

    private EntityIdReferenceDto schedule;
    private PlannedDayDto plannedDay;
    private DateDto date;


    public EntityIdReferenceDto getSchedule() {
        return schedule;
    }

    public void setSchedule(EntityIdReferenceDto schedule) {
        this.schedule = schedule;
    }

    public PlannedDayDto getPlannedDay() {
        return plannedDay;
    }

    public void setPlannedDay(PlannedDayDto plannedDay) {
        this.plannedDay = plannedDay;
    }

    public DateDto getDate() {
        return date;
    }

    public void setDate(DateDto date) {
        this.date = date;
    }
}