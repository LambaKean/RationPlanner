package com.lambakean.RationPlanner.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class InvalidEntityException extends RuntimeException {

    private final BindingResult bindingResult;
}
