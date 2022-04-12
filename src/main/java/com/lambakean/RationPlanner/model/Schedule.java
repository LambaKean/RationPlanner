package com.lambakean.RationPlanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class Schedule extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "planned_day_id", nullable = false)
    private PlannedDay plannedDay;

    private LocalDate startDate;

    private Integer nextRepeatAfterDays;

    public String getPlannedDayId() {

        if(plannedDay == null) return null;
        return plannedDay.getId();
    }
}