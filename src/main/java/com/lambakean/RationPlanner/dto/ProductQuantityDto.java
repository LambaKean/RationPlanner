package com.lambakean.RationPlanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductQuantityDto {

    private String id;
    private Double amount;
    private MeasurementUnitDto measurementUnitDto;

    public ProductQuantityDto(String id, Double amount, MeasurementUnitDto measurementUnitDto) {
        this.id = id;
        this.amount = amount;
        this.measurementUnitDto = measurementUnitDto;
    }

    public ProductQuantityDto() {}

    @JsonIgnore
    public String getMeasurementUnitId() {

        if(measurementUnitDto == null) {
            return null;
        }

        return measurementUnitDto.getId();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public MeasurementUnitDto getMeasurementUnit() {
        return measurementUnitDto;
    }

    public void setMeasurementUnit(MeasurementUnitDto measurementUnitDto) {
        this.measurementUnitDto = measurementUnitDto;
    }
}
