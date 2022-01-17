package com.lambakean.RationPlanner.dto;

import com.lambakean.RationPlanner.model.MeasurementUnit;

public class MeasurementUnitDto {

    private String id;
    private String name;

    public MeasurementUnitDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MeasurementUnitDto() {}

    public MeasurementUnit toMeasurementUnit() {

        MeasurementUnit measurementUnit = new MeasurementUnit();

        measurementUnit.setId(id);
        measurementUnit.setName(name);

        return measurementUnit;
    }

    public static MeasurementUnitDto fromMeasurementUnit(MeasurementUnit measurementUnit) {

        if(measurementUnit == null) {
            return null;
        }

        return new MeasurementUnitDto(
                measurementUnit.getId(),
                measurementUnit.getName()
        );
    }


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