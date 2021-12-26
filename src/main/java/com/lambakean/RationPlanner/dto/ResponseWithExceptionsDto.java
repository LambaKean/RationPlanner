package com.lambakean.RationPlanner.dto;

import java.util.Set;

public class ResponseWithExceptionsDto {

    private Set<? extends ExceptionDto> exceptions;

    public ResponseWithExceptionsDto(Set<? extends ExceptionDto> exceptions) {
        this.exceptions = exceptions;
    }

    public ResponseWithExceptionsDto() {}


    public Set<? extends ExceptionDto> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Set<? extends ExceptionDto> exceptions) {
        this.exceptions = exceptions;
    }
}
