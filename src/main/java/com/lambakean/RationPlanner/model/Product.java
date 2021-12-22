package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String producer;

    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_quantity_id", referencedColumnName = "id", nullable = false)
    private ProductQuantity quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "meal", fetch = FetchType.LAZY)
    private Set<Ingredient> relatedIngredients;

    public Product(String name, String producer, Double price, ProductQuantity quantity, User user) {
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }

    public Product() {}

    public Double quantityDifferenceCoef(ProductQuantity productQuantity) {
        return productQuantity.getAmount() / quantity.getAmount();
    }

    public Double getQuantityAmount() {
        return this.quantity.getAmount();
    }

    public String getQuantityMeasurementUnitName() {
        return this.quantity.getMeasurementUnitName();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductQuantity getQuantity() {
        return quantity;
    }

    public void setQuantity(ProductQuantity quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Ingredient> getRelatedIngredients() {
        return relatedIngredients;
    }

    public void setRelatedIngredients(Set<Ingredient> relatedIngredients) {
        this.relatedIngredients = relatedIngredients;
    }
}
