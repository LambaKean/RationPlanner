package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.model.ProductQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Component
public class ProductQuantityServiceImpl implements ProductQuantityService {

    private final Validator productQuantityValidator;

    @Autowired
    public ProductQuantityServiceImpl(Validator productQuantityValidator) {
        this.productQuantityValidator = productQuantityValidator;
    }

    @Override
    public void validate(ProductQuantity productQuantity) {

        DataBinder dataBinder = new DataBinder(productQuantity);
        dataBinder.addValidators(productQuantityValidator);

        dataBinder.validate();

        BindingResult productQuantityBindingResult = dataBinder.getBindingResult();

        if(productQuantityBindingResult.hasErrors()) {
            throw new InvalidEntityException(productQuantityBindingResult);
        }
    }
}
