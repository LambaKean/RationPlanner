package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "planned_days")
public class PlannedDay extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "plannedDay", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PlannedDayMeal> plannedDayMeals;

    @OneToMany(mappedBy = "plannedDay", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Schedule> schedules;

    public PlannedDay(String name, User user, List<PlannedDayMeal> plannedDayMeals) {
        this.name = name;
        this.user = user;
        this.plannedDayMeals = plannedDayMeals;
    }

    public PlannedDay() {}

    public Double getPrice() {

        if(plannedDayMeals == null) return 0.0;

        double totalPrice = 0.0;

        for(PlannedDayMeal plannedDayMeal : plannedDayMeals) {
            totalPrice += plannedDayMeal.getPrice();
        }

        return totalPrice;
    }

    public Integer getAmountOfMeals() {

        if(plannedDayMeals == null) return 0;
        return plannedDayMeals.size();
    }

    public String getUserId() {

        if(user == null) return null;
        return user.getId();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PlannedDayMeal> getPlannedDayMeals() {
        return plannedDayMeals;
    }

    public void setPlannedDayMeals(List<PlannedDayMeal> plannedDayMeals) {
        this.plannedDayMeals = plannedDayMeals;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}