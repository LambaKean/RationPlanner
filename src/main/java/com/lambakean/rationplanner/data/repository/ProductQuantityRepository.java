package com.lambakean.rationplanner.data.repository;

import com.lambakean.rationplanner.data.model.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, String> {}
