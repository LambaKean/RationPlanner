package com.lambakean.RationPlanner.data.repository;

import com.lambakean.RationPlanner.data.model.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, String> {}
