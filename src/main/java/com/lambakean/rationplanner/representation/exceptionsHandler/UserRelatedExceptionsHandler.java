package com.lambakean.rationplanner.representation.exceptionsHandler;

import com.lambakean.rationplanner.representation.dto.ResponseWithExceptionsDto;
import com.lambakean.rationplanner.representation.dto.ExceptionDto;
import com.lambakean.rationplanner.domain.exception.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class UserRelatedExceptionsHandler {

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<ResponseWithExceptionsDto> handleUserNotLoggedInException(UserNotLoggedInException e) {

        ExceptionDto exceptionDto = new ExceptionDto("userNotLoggedIn", e.getMessage());

        Set<ExceptionDto> exceptionDtos = new HashSet<>();
        exceptionDtos.add(exceptionDto);

        ResponseWithExceptionsDto responseDto = new ResponseWithExceptionsDto();
        responseDto.setExceptions(exceptionDtos);

        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }
}
