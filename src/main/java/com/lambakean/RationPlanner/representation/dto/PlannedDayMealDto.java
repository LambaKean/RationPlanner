package com.lambakean.rationplanner.representation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlannedDayMealDto {

    private String id;
    private EntityIdReferenceDto meal;
    private TimeDto time;
}
