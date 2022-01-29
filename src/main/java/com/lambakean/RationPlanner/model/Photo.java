package com.lambakean.RationPlanner.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "photos")
public class Photo extends BaseEntity {

    private String extension;

    @OneToOne(mappedBy = "photo")
    private Product product;

    @OneToOne(mappedBy = "photo")
    private Meal meal;

    public Photo(String extension) {
        this.extension = extension;
    }

    public Photo() {}


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
}
