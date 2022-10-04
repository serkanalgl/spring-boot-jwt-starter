package com.serkanalgl.jwt.starter.auth.controller;

import com.serkanalgl.jwt.starter.auth.model.RefreshToken;
import com.serkanalgl.jwt.starter.auth.dto.AuthTokenDTO;
import com.serkanalgl.jwt.starter.auth.dto.LoginDTO;
import com.serkanalgl.jwt.starter.auth.dto.RegisterDTO;
import com.serkanalgl.jwt.starter.auth.service.TokenService;
import com.serkanalgl.jwt.starter.common.exception.BadRequestException;
import com.serkanalgl.jwt.starter.common.exception.NotFoundException;
import com.serkanalgl.jwt.starter.user.model.User;
import com.serkanalgl.jwt.starter.user.dto.UserCreateDTO;
import com.serkanalgl.jwt.starter.user.dto.UserDTO;
import com.serkanalgl.jwt.starter.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;


    @PostMapping("/register")
    @Operation(summary = "Register", description = "Creates a new user specified with user type (CUSTOMER, MERCHANT)")
    public UserDTO register(@RequestBody @Validated RegisterDTO registerDTO,
                            @Parameter(description = "Set true to register merchant user", example = "false")
                            @RequestParam(value = "merchant", required = false) boolean isMerchant){

        UserCreateDTO userCreateDto = new UserCreateDTO();
        userCreateDto.setFirstName(registerDTO.getFirstName());
        userCreateDto.setLastName(registerDTO.getLastName());
        userCreateDto.setEmail(registerDTO.getEmail());
        userCreateDto.setPassword(registerDTO.getPassword());

        if(isMerchant){
            userCreateDto.setRoles(Set.of("MERCHANT"));
        }else {
            userCreateDto.setRoles(Set.of("CUSTOMER"));
        }

        return userService.create(userCreateDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password", description = "Returns token details which includes access_token, refresh_token etc.")
    public AuthTokenDTO login(@RequestBody @Validated LoginDTO loginDTO){
       try {
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
           return buildTokenResponse((User) authentication.getPrincipal());
       }catch (BadCredentialsException e){
           throw new BadRequestException("INVALID_CREDENTIALS", "Invalid email or password");
       }catch (Exception e){
           throw new BadRequestException("AUTH_FAILED", e.getMessage());
       }
    }

    @PostMapping("/refresh_token/{refresh_token}")
    @Operation(summary = "Refresh short lived access_token")
    public AuthTokenDTO refreshToken(@Parameter(description = "Refresh token", required = true, name = "refresh_token") @PathVariable(name = "refresh_token") String refreshTokenRequest){

        Optional<RefreshToken> optionalRefreshToken = tokenService.findRefreshTokenById(refreshTokenRequest);
        if(!optionalRefreshToken.isPresent()){
            throw new NotFoundException("REFRESH_TOKEN_NOT_FOUND", "Refresh token not found");
        }

        RefreshToken refreshToken = optionalRefreshToken.get();

        Optional<User> optionalUser = userService.findById(refreshToken.getUserId());
        if(!optionalRefreshToken.isPresent()){
            throw new NotFoundException("USER_NOT_FOUND", "User not found");
        }

        if(refreshToken.getExpireAt().isBefore(LocalDateTime.now())){
            throw new BadRequestException("REFRESH_TOKEN_EXPIRED", "Refresh token expired");
        }

        return buildTokenResponse(optionalUser.get());
    }

    private AuthTokenDTO buildTokenResponse(User user){

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        AuthTokenDTO authTokenDTO = new AuthTokenDTO();
        authTokenDTO.setAccessToken(accessToken);
        authTokenDTO.setRefreshToken(refreshToken);

        return authTokenDTO;

    }

}
