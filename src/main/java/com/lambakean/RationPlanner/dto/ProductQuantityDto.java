package com.lambakean.RationPlanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lambakean.RationPlanner.model.ProductQuantity;

public class ProductQuantityDto {

    private String id;
    private Double amount;
    private MeasurementUnitDto measurementUnit;

    public ProductQuantityDto(String id, Double amount, MeasurementUnitDto measurementUnit) {
        this.id = id;
        this.amount = amount;
        this.measurementUnit = measurementUnit;
    }

    public ProductQuantityDto() {}

    public ProductQuantity toProductQuantity() {

        ProductQuantity productQuantity = new ProductQuantity();

        productQuantity.setId(id);
        productQuantity.setAmount(amount);
        productQuantity.setMeasurementUnit(measurementUnit.toMeasurementUnit());

        return productQuantity;
    }

    public static ProductQuantityDto fromProductQuantity(ProductQuantity productQuantity) {

        if(productQuantity == null) {
            return null;
        }

        ProductQuantityDto productQuantityDto = new ProductQuantityDto();
        productQuantityDto.setId(productQuantity.getId());
        productQuantityDto.setAmount(productQuantity.getAmount());
        productQuantityDto.setMeasurementUnit(
                MeasurementUnitDto.fromMeasurementUnit(
                        productQuantity.getMeasurementUnit()
                )
        );

        return productQuantityDto;
    }

    @JsonIgnore
    public String getMeasurementUnitId() {

        if(measurementUnit == null) {
            return null;
        }

        return measurementUnit.getId();
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
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnitDto measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
