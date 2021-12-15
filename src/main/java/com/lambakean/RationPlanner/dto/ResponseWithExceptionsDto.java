package com.lambakean.RationPlanner.dto;

import com.lambakean.RationPlanner.dto.exceptionDto.ExceptionDto;

import java.util.Set;

public class ResponseWithExceptionsDto {

    private Set<ExceptionDto> exceptions;

    public ResponseWithExceptionsDto(Set<ExceptionDto> exceptions) {
        this.exceptions = exceptions;
    }

    public ResponseWithExceptionsDto() {}


    public Set<ExceptionDto> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Set<ExceptionDto> exceptions) {
        this.exceptions = exceptions;
    }
}
