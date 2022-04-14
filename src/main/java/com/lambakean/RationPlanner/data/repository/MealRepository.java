package com.lambakean.rationplanner.data.repository;

import com.lambakean.rationplanner.data.model.Meal;
import com.lambakean.rationplanner.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, String> {

    boolean existsByIdAndUser(String id, User user);

    List<Meal> findAllByUser(User user);
}
