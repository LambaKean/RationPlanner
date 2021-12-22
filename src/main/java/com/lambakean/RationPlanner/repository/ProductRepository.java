package com.lambakean.RationPlanner.repository;

import com.lambakean.RationPlanner.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

//    @Override
//    @NonNull
//    Optional<Product> findById(@NonNull String id);

}