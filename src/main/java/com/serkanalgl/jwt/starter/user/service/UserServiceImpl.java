package com.serkanalgl.jwt.starter.user.service;

import com.serkanalgl.jwt.starter.common.exception.BadRequestException;
import com.serkanalgl.jwt.starter.common.exception.NotFoundException;
import com.serkanalgl.jwt.starter.user.dto.UserCreateDTO;
import com.serkanalgl.jwt.starter.user.dto.UserDTO;
import com.serkanalgl.jwt.starter.user.dto.UserUpdateDTO;
import com.serkanalgl.jwt.starter.user.dto.mapper.UserMapper;
import com.serkanalgl.jwt.starter.user.model.Role;
import com.serkanalgl.jwt.starter.user.repository.UserRepository;
import com.serkanalgl.jwt.starter.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Bean
    public Function<Jwt, User> fetchCurrentUser() {
        return jwt -> {
            String userId = jwt.getClaimAsString("sub");
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) throw new NotFoundException("USER_NOT_FOUND");

            return user.get();
        };
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(!optionalUser.isPresent()) throw new UsernameNotFoundException("User not found given with email");
        return optionalUser.get();
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDTO create(UserCreateDTO userCreateDto) {

        Optional<User> userOptional = userRepository.findByEmail(userCreateDto.getEmail());
        if(userOptional.isPresent()) throw new BadRequestException("USER_ALREADY_EXIST", "User already exist");

        User user = userMapper.convert(userCreateDto);

        Set<String> roles = user.getRoles().stream().filter(role -> {
            try {
                return Role.valueOf(role) != null;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }).collect(Collectors.toSet());

        user.setRoles(roles);

        if(roles.isEmpty()) throw new BadRequestException("USER_ROLE_NOT_EXIST", "No role exist");

        User userResponse = userRepository.save(user);

        return userMapper.convert(userResponse);
    }

    @Override
    public UserDTO update(String userId, UserUpdateDTO userUpdateDto) {

        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) throw new NotFoundException("USER_NOT_FOUND", "User not found");

        User userDao = userOptional.get();

        userDao.setFirstName(userUpdateDto.getFirstName());
        userDao.setLastName(userUpdateDto.getLastName());
        userDao.setEmail(userUpdateDto.getEmail());

        User user = userRepository.save(userDao);

        return userMapper.convert(user);
    }

    @Override
    public void bootstrap(List<User> users) {

        users.stream().forEach(user -> {
            Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
            if(!optionalUser.isPresent()){
                userRepository.save(user);
            }
        });
    }


}
