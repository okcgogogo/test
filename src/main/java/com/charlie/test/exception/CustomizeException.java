package com.charlie.test.exception;

public class CustomizeException extends RuntimeException{
    private  Integer code;
    private  String message;

    public CustomizeException(CustomizeErrorCode errorInfo) {
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMessage();
    }


    @Override
    public String getMessage() {
        return message;
    }


    public Integer getCode() {
        return code;
    }
}
