package com.lambakean.RationPlanner.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "photos")
public class Photo extends BaseEntity {

    @Lob
    private byte[] content;

    @OneToOne(mappedBy = "photo")
    private Product product;

    @OneToOne(mappedBy = "photo")
    private Meal meal;

    public Photo(byte[] content) {
        this.content = content;
    }

    public Photo() {}


    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
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
