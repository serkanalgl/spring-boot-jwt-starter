package com.serkanalgl.jwt.starter.user.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

public enum Role {

    ADMIN, MERCHANT, CUSTOMER

}
