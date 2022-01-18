package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.ProductQuantityDto;
import com.lambakean.RationPlanner.model.ProductQuantity;
import com.lambakean.RationPlanner.repository.ProductQuantityRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductQuantityDtoConverter {

    private final ProductQuantityRepository productQuantityRepository;
    private final MeasurementUnitDtoConverter measurementUnitDtoConverter;

    public ProductQuantityDtoConverter(ProductQuantityRepository productQuantityRepository,
                                       MeasurementUnitDtoConverter measurementUnitDtoConverter) {
        this.productQuantityRepository = productQuantityRepository;
        this.measurementUnitDtoConverter = measurementUnitDtoConverter;
    }

    public ProductQuantity toProductQuantity(ProductQuantityDto productQuantityDto) {

        if(productQuantityDto == null) {
            return null;
        }

        ProductQuantity productQuantity = new ProductQuantity();

        if(productQuantityDto.getId() != null) {
            return productQuantityRepository.findById(productQuantityDto.getId()).orElse(null);
        }

        productQuantity.setAmount(productQuantityDto.getAmount());
        productQuantity.setMeasurementUnit(
                measurementUnitDtoConverter.toMeasurementUnit(productQuantityDto.getMeasurementUnit())
        );

        return productQuantity;
    }

    public ProductQuantityDto toProductQuantityDto(ProductQuantity productQuantity) {

        if(productQuantity == null) {
            return null;
        }

        ProductQuantityDto productQuantityDto = new ProductQuantityDto();

        productQuantityDto.setId(productQuantity.getId());
        productQuantityDto.setAmount(productQuantity.getAmount());
        productQuantityDto.setMeasurementUnit(
                measurementUnitDtoConverter.toMeasurementUnitDto(productQuantity.getMeasurementUnit())
        );

        return  productQuantityDto;
    }
}
