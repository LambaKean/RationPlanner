package com.lambakean.rationplanner.representation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduledPlannedDayDto {

    private EntityIdReferenceDto schedule;
    private PlannedDayDto plannedDay;
    private DateDto date;
}