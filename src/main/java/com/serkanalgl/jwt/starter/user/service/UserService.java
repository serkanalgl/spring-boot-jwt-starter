package com.serkanalgl.jwt.starter.user.service;

import com.serkanalgl.jwt.starter.user.dto.UserCreateDTO;
import com.serkanalgl.jwt.starter.user.dto.UserDTO;
import com.serkanalgl.jwt.starter.user.dto.UserUpdateDTO;
import com.serkanalgl.jwt.starter.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findById(String id);

    UserDTO create(UserCreateDTO userCreateDto);

    UserDTO update(String userId, UserUpdateDTO userUpdateDto);

    void bootstrap(List<User> users);

}
