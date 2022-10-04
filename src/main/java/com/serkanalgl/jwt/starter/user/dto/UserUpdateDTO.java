package com.serkanalgl.jwt.starter.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {

    @NotEmpty
    @Length(max = 256)
    private String firstName;

    @NotEmpty
    @Length(max = 256)
    private String lastName;

    @Email
    @NotEmpty
    private String email;

}
