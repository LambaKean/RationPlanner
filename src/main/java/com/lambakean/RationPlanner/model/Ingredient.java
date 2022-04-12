package com.lambakean.RationPlanner.model;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_quantity_id", nullable = false)
    private ProductQuantity productQuantity;

    public Ingredient(String name, Product product, Meal meal, ProductQuantity productQuantity) {
        this.name = name;
        this.product = product;
        this.meal = meal;
        this.productQuantity = productQuantity;
    }

    public Ingredient() {}

    public Double getPrice() {

        if(product == null || product.getPrice() == null) {
            return 0.0;
        }

        return product.getPrice() * product.quantityDifferenceCoef(productQuantity);
    }

    String getProductId() {

        if(product == null) return null;
        return product.getId();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public ProductQuantity getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(ProductQuantity productQuantity) {
        this.productQuantity = productQuantity;
    }
}