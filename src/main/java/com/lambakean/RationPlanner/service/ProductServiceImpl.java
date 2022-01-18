package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ProductDto;
import com.lambakean.RationPlanner.dto.converter.ProductDtoConverter;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.model.MeasurementUnit;
import com.lambakean.RationPlanner.model.Product;
import com.lambakean.RationPlanner.model.ProductQuantity;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.MeasurementUnitRepository;
import com.lambakean.RationPlanner.repository.ProductRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final PrincipalService principalService;
    private final Validator productValidator;
    private final ProductQuantityService productQuantityService;
    private final ProductDtoConverter productDtoConverter;

    public ProductServiceImpl(ProductRepository productRepository,
                              MeasurementUnitRepository measurementUnitRepository,
                              PrincipalService principalService,
                              Validator productValidator,
                              ProductQuantityService productQuantityService,
                              ProductDtoConverter productDtoConverter) {
        this.productRepository = productRepository;
        this.measurementUnitRepository = measurementUnitRepository;
        this.principalService = principalService;
        this.productValidator = productValidator;
        this.productQuantityService = productQuantityService;
        this.productDtoConverter = productDtoConverter;
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

        productQuantityService.validate(productQuantity);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setProducer(productDto.getProducer());
        product.setQuantity(productQuantity);
        product.setPrice(productDto.getPrice());
        product.setUser(user);

        validate(product);

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

    public void validate(Product product) {

        DataBinder dataBinder = new DataBinder(product, "product");
        dataBinder.addValidators(productValidator);

        dataBinder.validate();

        BindingResult productBindingResult = dataBinder.getBindingResult();
        if(productBindingResult.hasErrors()) {
            throw new InvalidEntityException(productBindingResult);
        }
    }
}