package com.lambakean.RationPlanner.dto.form;

import com.lambakean.RationPlanner.dto.DateDto;
import com.lambakean.RationPlanner.dto.EntityIdReferenceDto;

public class ScheduleCreationForm {

    private EntityIdReferenceDto plannedDay;
    private DateDto startDate;
    private Integer nextRepeatAfterDays;


    public EntityIdReferenceDto getPlannedDay() {
        return plannedDay;
    }

    public void setPlannedDay(EntityIdReferenceDto plannedDay) {
        this.plannedDay = plannedDay;
    }

    public DateDto getStartDate() {
        return startDate;
    }

    public void setStartDate(DateDto startDate) {
        this.startDate = startDate;
    }

    public Integer getNextRepeatAfterDays() {
        return nextRepeatAfterDays;
    }

    public void setNextRepeatAfterDays(Integer nextRepeatAfterDays) {
        this.nextRepeatAfterDays = nextRepeatAfterDays;
    }
}
