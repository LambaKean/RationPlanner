package com.lambakean.RationPlanner.dto.form;

import com.lambakean.RationPlanner.dto.EntityIdReferenceDto;
import com.lambakean.RationPlanner.dto.PhotoDto;

public class ProductCreationForm {

    private String name;
    private String producer;
    private EntityIdReferenceDto quantity;
    private Double price;
    private PhotoDto photoDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public EntityIdReferenceDto getQuantity() {
        return quantity;
    }

    public void setQuantity(EntityIdReferenceDto quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PhotoDto getPhotoDto() {
        return photoDto;
    }

    public void setPhotoDto(PhotoDto photoDto) {
        this.photoDto = photoDto;
    }
}
