package com.lambakean.RationPlanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lambakean.RationPlanner.model.Product;

public class ProductDto {

    private String id;
    private String name;
    private String producer;
    private ProductQuantityDto quantity;
    private Double price;

    public ProductDto(String id, String name, String producer, ProductQuantityDto quantity, Double price) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductDto() {}

    public Product toProduct() {

        Product product = new Product();

        product.setId(id);
        product.setName(name);
        product.setProducer(producer);
        product.setQuantity(quantity != null ? quantity.toProductQuantity() : null);
        product.setPrice(price);

        return product;
    }

    public static ProductDto fromProduct(Product product) {

        if(product == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setProducer(product.getProducer());
        productDto.setQuantity(ProductQuantityDto.fromProductQuantity(product.getQuantity()));
        productDto.setPrice(product.getPrice());

        return productDto;
    }

    @JsonIgnore
    public String getMeasurementUnitId() {

        if(quantity == null) {
            return null;
        }

        return quantity.getMeasurementUnitId();
    }

    @JsonIgnore
    public Double getQuantityAmount() {

        if(quantity == null) {
            return null;
        }

        return quantity.getAmount();
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
        return quantity;
    }

    public void setQuantity(ProductQuantityDto quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
