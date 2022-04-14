package com.lambakean.rationplanner.data.repository;

import com.lambakean.rationplanner.data.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {}
