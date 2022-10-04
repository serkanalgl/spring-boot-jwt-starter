package com.serkanalgl.jwt.starter.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

@ConfigurationProperties(prefix = "auth.jwt")
@Slf4j
public record JwtProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey, Long accessTokenLifetimeSeconds, Long refreshTokenLifetimeSeconds) {

    @Override
    public Long accessTokenLifetimeSeconds() {
        return Optional.ofNullable(accessTokenLifetimeSeconds).orElse(Long.valueOf(60 * 60));
    }

    @Override
    public Long refreshTokenLifetimeSeconds() {
        return Optional.ofNullable(refreshTokenLifetimeSeconds).orElse(Long.valueOf(60 * 60 * 24 * 14));
    }
}
