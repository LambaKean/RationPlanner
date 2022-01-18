package com.lambakean.RationPlanner.dto;

public class ScheduleDto {

    private String id;
    private PlannedDayDto plannedDayDto;
    private DateDto startDateDto;
    private Integer nextRepeatAfterDays;

    public ScheduleDto(String id, PlannedDayDto plannedDayDto, DateDto startDateDto, Integer nextRepeatAfterDays) {
        this.id = id;
        this.plannedDayDto = plannedDayDto;
        this.startDateDto = startDateDto;
        this.nextRepeatAfterDays = nextRepeatAfterDays;
    }

    public ScheduleDto() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlannedDayDto getPlannedDay() {
        return plannedDayDto;
    }

    public void setPlannedDay(PlannedDayDto plannedDayDto) {
        this.plannedDayDto = plannedDayDto;
    }

    public DateDto getStartDate() {
        return startDateDto;
    }

    public void setStartDate(DateDto startDateDto) {
        this.startDateDto = startDateDto;
    }

    public Integer getNextRepeatAfterDays() {
        return nextRepeatAfterDays;
    }

    public void setNextRepeatAfterDays(Integer nextRepeatAfterDays) {
        this.nextRepeatAfterDays = nextRepeatAfterDays;
    }
}
