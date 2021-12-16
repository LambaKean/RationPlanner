package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "planned_days")
public class PlannedDay extends BaseEntity {

    private String name;

    @ManyToMany
    @JoinTable(
            name = "planned_days_meals",
            joinColumns = { @JoinColumn(name = "planned_day_id") },
            inverseJoinColumns = { @JoinColumn(name = "meal_id") }
    )
    private Set<Meal> meals;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PlannedDay(String name, Set<Meal> meals, User user) {
        this.name = name;
        this.meals = meals;
        this.user = user;
    }

    public PlannedDay() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}