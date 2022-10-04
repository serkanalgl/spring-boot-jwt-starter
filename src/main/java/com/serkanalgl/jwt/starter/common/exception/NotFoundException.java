package com.serkanalgl.jwt.starter.common.exception;

public class NotFoundException extends RuntimeException{

    private String code;

    public NotFoundException(String code) {
        super("");
        this.code = code;
    }
    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
