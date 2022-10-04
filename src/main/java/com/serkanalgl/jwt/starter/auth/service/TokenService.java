package com.serkanalgl.jwt.starter.auth.service;

import com.serkanalgl.jwt.starter.auth.model.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface TokenService {

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    Optional<RefreshToken> findRefreshTokenById(String refreshToken);
}
