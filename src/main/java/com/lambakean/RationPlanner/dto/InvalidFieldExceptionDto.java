package com.lambakean.RationPlanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidFieldExceptionDto extends ExceptionDto {

    private String targetObjectName;
    private String field;
    private String fieldCode;
}
