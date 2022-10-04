package com.serkanalgl.jwt.starter.auth.dto;

import lombok.Data;

@Data
public class AuthTokenDTO {

    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;

}
