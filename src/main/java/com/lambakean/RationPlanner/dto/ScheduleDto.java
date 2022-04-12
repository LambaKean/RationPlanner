package com.lambakean.RationPlanner.dto;

public class ScheduleDto {

    private String id;
    private PlannedDayDto plannedDay;
    private DateDto startDate;
    private Integer nextRepeatAfterDays;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlannedDayDto getPlannedDay() {
        return plannedDay;
    }

    public void setPlannedDay(PlannedDayDto plannedDayDto) {
        this.plannedDay = plannedDayDto;
    }

    public DateDto getStartDate() {
        return startDate;
    }

    public void setStartDate(DateDto startDateDto) {
        this.startDate = startDateDto;
    }

    public Integer getNextRepeatAfterDays() {
        return nextRepeatAfterDays;
    }

    public void setNextRepeatAfterDays(Integer nextRepeatAfterDays) {
        this.nextRepeatAfterDays = nextRepeatAfterDays;
    }
}
