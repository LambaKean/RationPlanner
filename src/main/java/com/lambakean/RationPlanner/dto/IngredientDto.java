package com.lambakean.RationPlanner.dto;

import com.lambakean.RationPlanner.model.Ingredient;

public class IngredientDto {

    private String id;
    private String name;
    private ProductDto product;
    private ProductQuantityDto productQuantity;
    private Double price;

    public IngredientDto(String id, String name, ProductDto product, ProductQuantityDto productQuantity, Double price) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.productQuantity = productQuantity;
        this.price = price;
    }

    public IngredientDto() {}

    public Ingredient toIngredient() {

        Ingredient ingredient = new Ingredient();

        ingredient.setId(id);
        ingredient.setName(name);
        ingredient.setProduct(product != null ? product.toProduct() : null);
        ingredient.setProductQuantity(productQuantity != null ? productQuantity.toProductQuantity() : null);

        return ingredient;
    }

    public static IngredientDto fromIngredient(Ingredient ingredient) {

        IngredientDto ingredientDto = new IngredientDto();

        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setProduct(
                ProductDto.fromProduct(ingredient.getProduct())
        );
        ingredientDto.setProductQuantity(
                ProductQuantityDto.fromProductQuantity(ingredient.getProductQuantity())
        );
        ingredientDto.setPrice(ingredient.getPrice());

        return ingredientDto;
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

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public ProductQuantityDto getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(ProductQuantityDto productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
