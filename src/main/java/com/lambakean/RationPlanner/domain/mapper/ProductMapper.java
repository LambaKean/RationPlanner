package com.lambakean.RationPlanner.domain.mapper;

import com.lambakean.RationPlanner.data.model.Product;
import com.lambakean.RationPlanner.representation.dto.ProductDto;
import com.lambakean.RationPlanner.representation.dto.form.ProductCreationForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    Product toProduct(ProductCreationForm productCreationForm);
}
