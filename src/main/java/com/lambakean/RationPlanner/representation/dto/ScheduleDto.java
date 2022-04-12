package com.lambakean.RationPlanner.representation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDto {

    private String id;
    private PlannedDayDto plannedDay;
    private DateDto startDate;
    private Integer nextRepeatAfterDays;
}
