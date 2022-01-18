package com.lambakean.RationPlanner.repository;

import com.lambakean.RationPlanner.model.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, String> {}
