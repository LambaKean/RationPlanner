package com.lambakean.RationPlanner.service;

import org.springframework.validation.Validator;

public interface ValidationService {

    void throwExceptionIfObjectIsInvalid(Object target, String objectName, Validator ...validators);

}
