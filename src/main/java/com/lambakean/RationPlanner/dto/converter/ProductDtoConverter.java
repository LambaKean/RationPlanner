package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.ProductDto;
import com.lambakean.RationPlanner.model.Product;
import com.lambakean.RationPlanner.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoConverter {

    private final ProductRepository productRepository;
    private final ProductQuantityDtoConverter productQuantityDtoConverter;

    public ProductDtoConverter(ProductRepository productRepository,
                               ProductQuantityDtoConverter productQuantityDtoConverter) {
        this.productRepository = productRepository;
        this.productQuantityDtoConverter = productQuantityDtoConverter;
    }

    public Product toProduct(ProductDto productDto) {

        if(productDto == null) {
            return null;
        }

        Product product = new Product();

        if(productDto.getId() != null) {
            return productRepository.findById(productDto.getId()).orElse(null);
        }

        product.setName(productDto.getName());
        product.setProducer(productDto.getProducer());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productQuantityDtoConverter.toProductQuantity(productDto.getQuantity()));

        return product;
    }

    public ProductDto toProductDto(Product product) {

        if(product == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setProducer(product.getProducer());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(productQuantityDtoConverter.toProductQuantityDto(product.getQuantity()));

        return productDto;
    }
}
