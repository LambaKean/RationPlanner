package com.lambakean.RationPlanner.dto;

public class IngredientDto {

    private String id;
    private String name;
    private ProductDto productDto;
    private ProductQuantityDto productQuantityDto;
    private Double price;

    public IngredientDto(String id, String name, ProductDto productDto, ProductQuantityDto productQuantityDto, Double price) {
        this.id = id;
        this.name = name;
        this.productDto = productDto;
        this.productQuantityDto = productQuantityDto;
        this.price = price;
    }

    public IngredientDto() {}


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

    public ProductDto getProduct() {
        return productDto;
    }

    public void setProduct(ProductDto productDto) {
        this.productDto = productDto;
    }

    public ProductQuantityDto getProductQuantity() {
        return productQuantityDto;
    }

    public void setProductQuantity(ProductQuantityDto productQuantityDto) {
        this.productQuantityDto = productQuantityDto;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
