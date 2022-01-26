package com.lambakean.RationPlanner.dto;

public class ScheduledPlannedDayDto {

    private String scheduleId;
    private DateDto dateDto;
    private PlannedDayDto plannedDayDto;

    public ScheduledPlannedDayDto(String scheduleId, DateDto dateDto, PlannedDayDto plannedDayDto) {
        this.scheduleId = scheduleId;
        this.dateDto = dateDto;
        this.plannedDayDto = plannedDayDto;
    }

    public ScheduledPlannedDayDto() {}


    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public DateDto getDate() {
        return dateDto;
    }

    public void setDate(DateDto dateDto) {
        this.dateDto = dateDto;
    }

    public PlannedDayDto getPlannedDay() {
        return plannedDayDto;
    }

    public void setPlannedDay(PlannedDayDto plannedDayDto) {
        this.plannedDayDto = plannedDayDto;
    }
}