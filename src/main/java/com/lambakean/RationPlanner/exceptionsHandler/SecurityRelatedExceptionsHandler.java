package com.lambakean.RationPlanner.exceptionsHandler;

import com.lambakean.RationPlanner.dto.ResponseWithExceptionsDto;
import com.lambakean.RationPlanner.dto.ExceptionDto;
import com.lambakean.RationPlanner.exception.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Set;

@ControllerAdvice
public class SecurityRelatedExceptionsHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseWithExceptionsDto> handleAuthenticationException(AuthenticationException e) {

        ExceptionDto exceptionDto = new ExceptionDto("authenticationException", e.getMessage());

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto();
        responseDto.setExceptions(Collections.singleton(exceptionDto));

        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }



}
