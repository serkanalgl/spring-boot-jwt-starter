package com.serkanalgl.jwt.starter.auth.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "refresh_tokens")
@Data
public class RefreshToken {

    @Id
    private String refreshToken;
    private String userId;
    private LocalDateTime expireAt;

}
