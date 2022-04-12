package com.lambakean.RationPlanner.representation.dto.form;

import com.lambakean.RationPlanner.representation.dto.DateDto;
import com.lambakean.RationPlanner.representation.dto.EntityIdReferenceDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCreationForm {

    private EntityIdReferenceDto plannedDay;
    private DateDto startDate;
    private Integer nextRepeatAfterDays;
}
