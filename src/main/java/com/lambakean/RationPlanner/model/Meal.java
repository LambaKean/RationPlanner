package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.time.Duration;
import java.util.Set;

@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "cooking_duration", nullable = false)
    private Duration cookingDuration;

    private String recipe;

    @OneToMany(mappedBy = "meal", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ingredient> ingredients;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "meals")
    private Set<PlannedDay> relatedPlannedDays;

    public Meal(String name,
                String description,
                Duration cookingDuration,
                String recipe,
                Set<Ingredient> ingredients,
                User user) {
        this.name = name;
        this.description = description;
        this.cookingDuration = cookingDuration;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.user = user;
    }

    public Meal() {}

    public Double getPrice() {

        if(this.ingredients == null) {
            return 0.0;
        }

        Double totalPrice = 0.0;

        for(Ingredient ingredient : ingredients) {
            totalPrice += ingredient.getPrice();
        }

        return totalPrice;
    }

    public String getUserId() {
        if(user == null) {
            return null;
        }

        return user.getId();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getCookingDuration() {
        return cookingDuration;
    }

    public void setCookingDuration(Duration cookingDuration) {
        this.cookingDuration = cookingDuration;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PlannedDay> getRelatedPlannedDays() {
        return relatedPlannedDays;
    }

    public void setRelatedPlannedDays(Set<PlannedDay> relatedPlannedDays) {
        this.relatedPlannedDays = relatedPlannedDays;
    }
}