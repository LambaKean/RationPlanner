package com.lambakean.RationPlanner.exception;

import org.springframework.validation.BindingResult;

public class InvalidEntityException extends RuntimeException {

    private final BindingResult bindingResult;

    public InvalidEntityException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public InvalidEntityException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public InvalidEntityException(String message, Throwable cause, BindingResult bindingResult) {
        super(message, cause);
        this.bindingResult = bindingResult;
    }

    public InvalidEntityException(Throwable cause, BindingResult bindingResult) {
        super(cause);
        this.bindingResult = bindingResult;
    }

    public InvalidEntityException(String message,
                                  Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace,
                                  BindingResult bindingResult) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.bindingResult = bindingResult;
    }


    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
