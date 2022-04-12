package com.lambakean.RationPlanner.representation.controller;

import com.lambakean.RationPlanner.representation.dto.ProductDto;
import com.lambakean.RationPlanner.representation.dto.form.ProductCreationForm;
import com.lambakean.RationPlanner.domain.mapper.ProductMapper;
import com.lambakean.RationPlanner.data.model.Product;
import com.lambakean.RationPlanner.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {

        Product product = productService.getProductById(id);
        ProductDto productDto = productMapper.toProductDto(product);

        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {

        List<Product> products = productService.getCurrentUserProducts();
        List<ProductDto> productDots = products
                .stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDots);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductCreationForm productCreationForm) {

        Product product = productService.createProduct(productMapper.toProduct(productCreationForm));
        ProductDto productDto = productMapper.toProductDto(product);

        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id) {

        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }
}
