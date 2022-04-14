package com.lambakean.rationplanner.representation.dto.form;

import com.lambakean.rationplanner.representation.dto.DateDto;
import com.lambakean.rationplanner.representation.dto.EntityIdReferenceDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCreationForm {

    private EntityIdReferenceDto plannedDay;
    private DateDto startDate;
    private Integer nextRepeatAfterDays;
}
