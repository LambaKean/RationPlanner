package com.lambakean.RationPlanner.model;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @OneToOne
    @JoinColumn(name = "product_quantity_id", nullable = false)
    private ProductQuantity productQuantity;

    public Ingredient(Product product, Meal meal, ProductQuantity productQuantity) {
        this.product = product;
        this.meal = meal;
        this.productQuantity = productQuantity;
    }

    public Ingredient() {}

    public Double getPrice() {

        if(product == null) {
            throw new NullPointerException("Field \"product\" of Ingredient instance mustn't be null");
        }

        return product.getPrice() * product.quantityDifferenceCoef(productQuantity);
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