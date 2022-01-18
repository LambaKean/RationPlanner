package com.lambakean.RationPlanner.dto;

public class MeasurementUnitDto {

    private String id;
    private String name;

    public MeasurementUnitDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MeasurementUnitDto() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}