package com.lambakean.RationPlanner.dto;

public class InvalidFieldExceptionDto extends ExceptionDto {

    private String targetObjectName;
    private String field;
    private String fieldCode;

    public InvalidFieldExceptionDto(String code,
                                    String message,
                                    String targetObjectName,
                                    String field,
                                    String fieldCode) {
        super(code, message);
        this.targetObjectName = targetObjectName;
        this.field = field;
        this.fieldCode = fieldCode;
    }

    public InvalidFieldExceptionDto(String targetObjectName,
                                    String field,
                                    String fieldCode) {
        this.targetObjectName = targetObjectName;
        this.field = field;
        this.fieldCode = fieldCode;
    }

    public InvalidFieldExceptionDto() {}


    public String getTargetObjectName() {
        return targetObjectName;
    }

    public void setTargetObjectName(String targetObjectName) {
        this.targetObjectName = targetObjectName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }
}
