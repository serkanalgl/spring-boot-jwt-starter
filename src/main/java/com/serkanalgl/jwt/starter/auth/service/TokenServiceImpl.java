package com.serkanalgl.jwt.starter.auth.service;

import com.serkanalgl.jwt.starter.auth.config.JwtProperties;
import com.serkanalgl.jwt.starter.auth.repository.RefreshTokenRepository;
import com.serkanalgl.jwt.starter.auth.model.RefreshToken;
import com.serkanalgl.jwt.starter.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, Instant.now().plus(jwtProperties.accessTokenLifetimeSeconds(), ChronoUnit.SECONDS));
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {

        User user = (User) userDetails;

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getUserId());
        refreshToken.setExpireAt(LocalDateTime.now().plus(jwtProperties.refreshTokenLifetimeSeconds(), ChronoUnit.SECONDS));

        RefreshToken token = refreshTokenRepository.save(refreshToken);
        return token.getRefreshToken();
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenById(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken);
    }

    private String generateToken(UserDetails userDetails, Instant expiresAt){

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        User user = (User) userDetails;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(expiresAt)
                .subject(user.getUserId())
                .claim("roles", roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
}
