package com.lambakean.RationPlanner.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "schedules")
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


    public PlannedDay getPlannedDay() {
        return plannedDay;
    }

    public void setPlannedDay(PlannedDay plannedDay) {
        this.plannedDay = plannedDay;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getNextRepeatAfterDays() {
        return nextRepeatAfterDays;
    }

    public void setNextRepeatAfterDays(Integer nextRepeatAfterDays) {
        this.nextRepeatAfterDays = nextRepeatAfterDays;
    }
}