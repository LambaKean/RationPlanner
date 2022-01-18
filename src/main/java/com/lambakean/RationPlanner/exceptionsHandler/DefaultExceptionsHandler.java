package com.lambakean.RationPlanner.exceptionsHandler;

import com.lambakean.RationPlanner.dto.ExceptionDto;
import com.lambakean.RationPlanner.dto.ResponseWithExceptionsDto;
import com.lambakean.RationPlanner.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class DefaultExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseWithExceptionsDto> handleBadRequestException(BadRequestException e) {

        ExceptionDto exceptionDto = new ExceptionDto("badRequest", e.getMessage());

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto();
        responseDto.setExceptions(Collections.singleton(exceptionDto));

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
