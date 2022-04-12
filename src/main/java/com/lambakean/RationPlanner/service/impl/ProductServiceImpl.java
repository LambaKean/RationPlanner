package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.BadRequestException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final PrincipalService principalService;
    private final Validator productValidator;
    private final ValidationService validationService;
    private final ProductQuantityValidator productQuantityValidator;

    @Override
    public Product getProductById(String id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Продукт с id [%s] не существует", id))
        );

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просмотреть информацию об этом продукте"
        );

        if(!user.getId().equals(product.getUserId())) {
            throw new AccessDeniedException("Вы не имеете доступа к этому продукту.");
        }

        return product;
    }

    @Override
    @Transactional
    public List<Product> getCurrentUserProducts() {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы просматривать список своих продуктов"
        );

        return productRepository.getAllByUser(user);
    }

    @Override
    public Product createProduct(@NonNull Product productData) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность добавить продукт"
        );

        String measurementUnitId = productData.getMeasurementUnitId();

        if(measurementUnitId == null) {
            throw new BadRequestException("Вы не выбрали единицу измерения количества продукта");
        }

        MeasurementUnit measurementUnit = measurementUnitRepository.findById(measurementUnitId).orElseThrow(
                () -> new EntityNotFoundException("Вы выбрали невалидную единицу измерения количества продукта")
        );

        Double productQuantityAmount = productData.getQuantityAmount();
        ProductQuantity productQuantity = new ProductQuantity(productQuantityAmount, measurementUnit);

        validationService.validateThrowExceptionIfInvalid(productQuantity, productQuantityValidator);

        productData.setUser(user);
        productData.setQuantity(productQuantity);

        validationService.validateThrowExceptionIfInvalid(productData, productValidator);

        productRepository.saveAndFlush(productData);

        return productData;
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