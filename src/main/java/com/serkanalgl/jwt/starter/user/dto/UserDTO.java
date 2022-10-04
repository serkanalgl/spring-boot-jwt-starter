package com.serkanalgl.jwt.starter.user.dto;

import com.serkanalgl.jwt.starter.user.dto.BaseDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends BaseDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
}
