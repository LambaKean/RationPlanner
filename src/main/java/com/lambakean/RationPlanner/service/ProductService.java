package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(String id);

    List<ProductDto> getCurrentUserProducts();

    void deleteProductById(String id);
}
