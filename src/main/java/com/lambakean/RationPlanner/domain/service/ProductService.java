package com.lambakean.RationPlanner.domain.service;

import com.lambakean.RationPlanner.data.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product productData);

    Product getProductById(String id);

    List<Product> getCurrentUserProducts();

    void deleteProductById(String id);
}
