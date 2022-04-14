package com.lambakean.rationplanner.domain.mapper;

import com.lambakean.rationplanner.data.model.Product;
import com.lambakean.rationplanner.representation.dto.ProductDto;
import com.lambakean.rationplanner.representation.dto.form.ProductCreationForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    Product toProduct(ProductCreationForm productCreationForm);
}
