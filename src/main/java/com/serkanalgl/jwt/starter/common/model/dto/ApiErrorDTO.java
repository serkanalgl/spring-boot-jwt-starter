package com.serkanalgl.jwt.starter.common.model.dto;

import com.serkanalgl.jwt.starter.common.model.FieldError;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ApiErrorDTO {

    private String code;
    private String message;

    @Builder.Default
    private List<FieldError> fieldErrors = new ArrayList<>();

}
