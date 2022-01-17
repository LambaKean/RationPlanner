package com.lambakean.RationPlanner.dto;

import java.time.Duration;

public class DurationDto {

    private Integer hours;
    private Integer minutes;

    public DurationDto(Integer hours, Integer minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public DurationDto() {}

    public static DurationDto fromDuration(Duration duration) {

        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();

        return new DurationDto(hours, minutes);
    }


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
