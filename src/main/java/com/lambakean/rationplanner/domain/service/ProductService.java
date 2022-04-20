package com.lambakean.rationplanner.domain.service;

import com.lambakean.rationplanner.data.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product productData);

    Product getProductById(String id);

    List<Product> getCurrentUserProducts();

    void deleteProductById(String id);
}
