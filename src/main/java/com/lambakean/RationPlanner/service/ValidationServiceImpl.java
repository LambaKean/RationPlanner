package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.exception.InvalidEntityException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Component
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void throwExceptionIfObjectIsInvalid(Object target, String objectName, Validator... validators) {

        DataBinder dataBinder = new DataBinder(target, objectName);
        dataBinder.addValidators(validators);

        dataBinder.validate();

        BindingResult bindingResult = dataBinder.getBindingResult();
        if(bindingResult.hasErrors()) {
            throw new InvalidEntityException(bindingResult);
        }
    }
}
