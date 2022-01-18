package com.lambakean.RationPlanner.dto;

public class TimeDto {

    private Integer hours;
    private Integer minutes;

    public TimeDto(Integer hours, Integer minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public TimeDto() {}


    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
