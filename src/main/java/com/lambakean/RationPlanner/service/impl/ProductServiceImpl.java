package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.dto.ProductDto;
import com.lambakean.RationPlanner.dto.converter.ProductDtoConverter;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.MeasurementUnit;
import com.lambakean.RationPlanner.model.Product;
import com.lambakean.RationPlanner.model.ProductQuantity;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.MeasurementUnitRepository;
import com.lambakean.RationPlanner.repository.ProductRepository;
import com.lambakean.RationPlanner.service.PrincipalService;
import com.lambakean.RationPlanner.service.ProductService;
import com.lambakean.RationPlanner.service.ValidationService;
import com.lambakean.RationPlanner.validator.ProductQuantityValidator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final PrincipalService principalService;
    private final Validator productValidator;
    private final ProductDtoConverter productDtoConverter;
    private final ValidationService validationService;
    private final ProductQuantityValidator productQuantityValidator;

    public ProductServiceImpl(ProductRepository productRepository,
                              MeasurementUnitRepository measurementUnitRepository,
                              PrincipalService principalService,
                              Validator productValidator,
                              ProductDtoConverter productDtoConverter,
                              ValidationService validationService,
                              ProductQuantityValidator productQuantityValidator) {
        this.productRepository = productRepository;
        this.measurementUnitRepository = measurementUnitRepository;
        this.principalService = principalService;
        this.productValidator = productValidator;
        this.productDtoConverter = productDtoConverter;
        this.validationService = validationService;
        this.productQuantityValidator = productQuantityValidator;
    }

    @Override
    public ProductDto getProductById(String id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Продукт с id [%s] не существует", id))
        );

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просмотреть информацию об этом продукте"
        );

        if(!user.getId().equals(product.getUserId())) {
            throw new AccessDeniedException("Вы не имеете доступа к этому продукту.");
        }

        return productDtoConverter.toProductDto(product);
    }

    @Override
    @Transactional
    public List<ProductDto> getCurrentUserProducts() {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просматривать список своих продуктов"
        );

        return productRepository.getAllByUser(user)
                .stream()
                .map(productDtoConverter::toProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto createProduct(@NonNull ProductDto productDto) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность добавить продукт"
        );

        String measurementUnitId = productDto.getMeasurementUnitId();

        if(measurementUnitId == null || !measurementUnitRepository.existsById(measurementUnitId)) {
            throw new EntityNotFoundException("Вы выбрали невалидную единицу измерения количества продукта");
        }

        MeasurementUnit measurementUnit = measurementUnitRepository.getById(measurementUnitId);

        Double productQuantityAmount = productDto.getQuantityAmount();
        ProductQuantity productQuantity = new ProductQuantity(productQuantityAmount, measurementUnit);

        validationService.throwExceptionIfObjectIsInvalid(
                productQuantity,
                "productQuantity",
                productQuantityValidator
        );

        Product product = productDtoConverter.toProduct(productDto);
        product.setUser(user);

        validationService.throwExceptionIfObjectIsInvalid(product, "product", productValidator);

        productRepository.saveAndFlush(product);

        return productDtoConverter.toProductDto(product);
    }

    @Override
    public void deleteProductById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность удалять продукты"
        );

        if(id == null || !productRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Неверно указан идентификатор продукта, который вы хотите удалить");
        }

        productRepository.deleteById(id);
    }
}