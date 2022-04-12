package com.lambakean.RationPlanner.data.repository;

import com.lambakean.RationPlanner.data.model.Product;
import com.lambakean.RationPlanner.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> getAllByUser(User user);

    boolean existsByIdAndUser(String id, User user);
}