package com.lambakean.RationPlanner.data.repository;

import com.lambakean.RationPlanner.data.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {}
