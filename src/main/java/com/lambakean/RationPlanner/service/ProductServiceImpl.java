package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ProductDto;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.Product;
import com.lambakean.RationPlanner.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto getProductById(String id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Продукт с id [%s] не существует", id))
        );

        return ProductDto.fromProduct(product);
    }
}
