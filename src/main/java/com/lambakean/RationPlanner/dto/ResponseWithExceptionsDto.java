package com.lambakean.RationPlanner.dto;

import java.util.Set;

public class ResponseWithExceptionsDto {

    private Set<? extends ExceptionDto> exceptionDtos;

    public ResponseWithExceptionsDto(Set<? extends ExceptionDto> exceptionDtos) {
        this.exceptionDtos = exceptionDtos;
    }

    public ResponseWithExceptionsDto() {}


    public Set<? extends ExceptionDto> getExceptions() {
        return exceptionDtos;
    }

    public void setExceptions(Set<? extends ExceptionDto> exceptionDtos) {
        this.exceptionDtos = exceptionDtos;
    }
}
