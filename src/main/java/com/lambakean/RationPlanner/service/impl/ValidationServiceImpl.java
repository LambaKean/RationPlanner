package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.exception.InvalidEntityException;
import com.lambakean.RationPlanner.service.ValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Service
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
