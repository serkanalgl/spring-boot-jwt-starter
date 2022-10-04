package com.serkanalgl.jwt.starter.common.exception;

public class BadRequestException extends RuntimeException{

    private String code;

    public BadRequestException(String code) {
        super("");
        this.code = code;
    }
    public BadRequestException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
