package com.serkanalgl.jwt.starter.auth.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterDTO {

    @NotEmpty
    @Length(max = 256)
    private String firstName;

    @NotEmpty
    @Length(max = 256)
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
