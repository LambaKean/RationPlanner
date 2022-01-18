package com.lambakean.RationPlanner.dto;

public class DateDto {

    private Integer day;
    private Integer month;
    private Integer year;

    public DateDto(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public DateDto() {}


    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
