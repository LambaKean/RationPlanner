package com.lambakean.RationPlanner.dto.form;

import com.lambakean.RationPlanner.dto.DateDto;
import com.lambakean.RationPlanner.dto.EntityIdReferenceDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCreationForm {

    private EntityIdReferenceDto plannedDay;
    private DateDto startDate;
    private Integer nextRepeatAfterDays;
}
