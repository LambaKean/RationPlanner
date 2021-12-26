package com.lambakean.RationPlanner.exceptionsHandler;

import com.lambakean.RationPlanner.dto.ResponseWithExceptionsDto;
import com.lambakean.RationPlanner.dto.ExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;

@ControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        ExceptionDto exceptionDto = new ExceptionDto("invalidRequestBody", "Тело запроса невалидно.");

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto(Set.of(exceptionDto));
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);

    }
}
