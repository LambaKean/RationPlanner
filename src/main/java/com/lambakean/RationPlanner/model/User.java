package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Product> products;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Product> meals;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<PlannedDay> plannedDays;

    public User(String username, String password, Set<Product> products, Set<Product> meals) {
        this.username = username;
        this.password = password;
        this.products = products;
        this.meals = meals;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Product> getMeals() {
        return meals;
    }

    public void setMeals(Set<Product> meals) {
        this.meals = meals;
    }

    public Set<PlannedDay> getPlannedDays() {
        return plannedDays;
    }

    public void setPlannedDays(Set<PlannedDay> plannedDays) {
        this.plannedDays = plannedDays;
    }
}
