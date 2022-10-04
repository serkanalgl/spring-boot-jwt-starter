package com.serkanalgl.jwt.starter.auth.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    @Email
    @NotEmpty
    @Schema(description = "well formatted email address", required = true, example = "user@example.com")
    private String email;

    @NotEmpty
    @Schema(description = "password", required = true, example = "password")
    private String password;
}
