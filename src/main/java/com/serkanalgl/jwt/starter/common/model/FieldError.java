package com.serkanalgl.jwt.starter.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldError {

    private String field;
    private String code;
    private String message;

}
