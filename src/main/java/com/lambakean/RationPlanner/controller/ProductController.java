package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.ProductDto;
import com.lambakean.RationPlanner.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {

        ProductDto productDto = productService.getProductById(id);

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {

        List<ProductDto> productDots = productService.getCurrentUserProducts();

        return new ResponseEntity<>(productDots, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto incomingProductDto) {

        ProductDto outgoingProductDto = productService.createProduct(incomingProductDto);

        return new ResponseEntity<>(outgoingProductDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id) {

        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }
}
