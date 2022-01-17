package com.lambakean.RationPlanner.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "planned_days")
public class PlannedDay extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "plannedDay", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PlannedDayMeal> plannedDayMeals;

    @OneToMany(mappedBy = "plannedDay", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Schedule> schedules;

    public PlannedDay(String name, User user, Set<PlannedDayMeal> plannedDayMeals) {
        this.name = name;
        this.user = user;
        this.plannedDayMeals = plannedDayMeals;
    }

    public PlannedDay() {}


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
}