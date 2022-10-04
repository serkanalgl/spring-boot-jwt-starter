package com.serkanalgl.jwt.starter.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.serkanalgl.jwt.starter.user.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO extends BaseDTO {

    private String id;

    @NotEmpty
    @Length(max = 100)
    private String name;

    @Length(max = 256)
    private String description;

}

