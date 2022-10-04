package com.serkanalgl.jwt.starter.user.dto;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public class BaseDTO {

    private String createdBy;
    private LocalDateTime createdAt;

    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
