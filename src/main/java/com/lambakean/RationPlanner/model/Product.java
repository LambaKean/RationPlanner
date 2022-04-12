package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String producer;

    @Column(nullable = false)
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_quantity_id", referencedColumnName = "id", nullable = false)
    private ProductQuantity quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "id", unique = true)
    private Photo photo;

    @OneToMany(mappedBy = "meal", fetch = FetchType.LAZY)
    private Set<Ingredient> relatedIngredients;

    public Product(String name, String producer, Double price, ProductQuantity quantity, User user, Photo photo) {
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
        this.photo = photo;
    }

    public Product() {}

    public Double quantityDifferenceCoef(ProductQuantity productQuantity) {
        return productQuantity.getAmount() / quantity.getAmount();
    }

    public Double getQuantityAmount() {

        if(quantity == null) return null;
        return quantity.getAmount();
    }

    public String getMeasurementUnitName() {

        if(quantity == null) return null;
        return quantity.getMeasurementUnitName();
    }

    public String getUserId() {

        if(user == null) return null;
        return user.getId();
    }

    public String getMeasurementUnitId() {

        if(quantity == null) return null;
        return quantity.getMeasurementUnitId();
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Set<Ingredient> getRelatedIngredients() {
        return relatedIngredients;
    }

    public void setRelatedIngredients(Set<Ingredient> relatedIngredients) {
        this.relatedIngredients = relatedIngredients;
    }
}
