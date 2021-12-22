package com.lambakean.RationPlanner.dto;

import com.lambakean.RationPlanner.model.Product;
import org.springframework.lang.NonNull;

import java.text.DecimalFormat;

public class ProductDto {

    private String name;
    private String producer;
    private String quantity;
    private double price;

    public ProductDto(String name, String producer, String quantity, double price) {
        this.name = name;
        this.producer = producer;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductDto() {}

    public static ProductDto fromProduct(@NonNull Product product) {

        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setProducer(product.getProducer());
        productDto.setPrice(product.getPrice());

        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        productDto.setQuantity(
                decimalFormat.format(product.getQuantityAmount()) + " " + product.getQuantityMeasurementUnitName()
        );

        return productDto;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
