package com.lambakean.RationPlanner.model;

import javax.persistence.*;

@Entity
@Table(name = "quantities")
public class ProductQuantity extends BaseEntity {

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "measurement_unit_id", nullable = false)
    private MeasurementUnit measurementUnit;

    @OneToOne(mappedBy = "quantity")
    private Product product;

    @OneToOne(mappedBy = "productQuantity")
    private Ingredient ingredient;

    public ProductQuantity() {}

    public ProductQuantity(Double amount, MeasurementUnit measurementUnit) {
        this.amount = amount;
        this.measurementUnit = measurementUnit;
    }

    public String getMeasurementUnitName() {

        if(measurementUnit == null) return null;
        return measurementUnit.getName();
    }

    public String getMeasurementUnitId() {

        if(measurementUnit == null) return null;
        return measurementUnit.getId();
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
