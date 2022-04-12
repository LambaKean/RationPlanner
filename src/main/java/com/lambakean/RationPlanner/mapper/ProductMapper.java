package com.lambakean.RationPlanner.mapper;

import com.lambakean.RationPlanner.dto.ProductDto;
import com.lambakean.RationPlanner.dto.form.ProductCreationForm;
import com.lambakean.RationPlanner.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    Product toProduct(ProductCreationForm productCreationForm);
}
