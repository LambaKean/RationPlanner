package com.lambakean.RationPlanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductDto {

    private String id;
    private String name;
    private String producer;
    private ProductQuantityDto quantityDto;
    private Double price;
    private PhotoDto photoDto;

    public ProductDto(String id, String name, String producer, ProductQuantityDto quantityDto, Double price) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.quantityDto = quantityDto;
        this.price = price;
    }

    public ProductDto() {}

    @JsonIgnore
    public String getMeasurementUnitId() {

        if(quantityDto == null) {
            return null;
        }

        return quantityDto.getMeasurementUnitId();
    }

    @JsonIgnore
    public Double getQuantityAmount() {

        if(quantityDto == null) {
            return null;
        }

        return quantityDto.getAmount();
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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public ProductQuantityDto getQuantity() {
        return quantityDto;
    }

    public void setQuantity(ProductQuantityDto quantityDto) {
        this.quantityDto = quantityDto;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PhotoDto getPhoto() {
        return photoDto;
    }

    public void setPhoto(PhotoDto photoDto) {
        this.photoDto = photoDto;
    }
}
