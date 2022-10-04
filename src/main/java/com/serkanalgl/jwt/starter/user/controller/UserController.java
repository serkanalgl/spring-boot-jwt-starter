package com.serkanalgl.jwt.starter.user.controller;

import com.serkanalgl.jwt.starter.user.annotation.CurrentUser;
import com.serkanalgl.jwt.starter.user.model.User;
import com.serkanalgl.jwt.starter.user.dto.UserDTO;
import com.serkanalgl.jwt.starter.user.dto.UserUpdateDTO;
import com.serkanalgl.jwt.starter.user.dto.mapper.UserMapper;
import com.serkanalgl.jwt.starter.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer")
@Slf4j
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/me")
    @PostAuthorize("returnObject.getUserId() == authentication.name")
    @Operation(summary = "Fetch authenticated user")
    UserDTO me(@CurrentUser User user, Authentication authentication){
        return userMapper.convert(user);
    }

    @PutMapping
    @PreAuthorize("#userId == authentication.name")
    @Operation(summary = "Update authenticated user")
    UserDTO updateMe(@RequestBody @Validated UserUpdateDTO userUpdateDto, Authentication authentication){
        return userService.update(authentication.getName(), userUpdateDto);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.name")
    @Operation(summary = "Update user", description = "This operation is allowing for ADMIN role or authenticated user")
    UserDTO update(@PathVariable("userId") String userId, @RequestBody @Validated UserUpdateDTO userUpdateDto){
        return userService.update(userId, userUpdateDto);
    }
}
