package com.serkanalgl.jwt.starter.user.startup;

import com.serkanalgl.jwt.starter.user.model.Role;
import com.serkanalgl.jwt.starter.user.model.User;
import com.serkanalgl.jwt.starter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${auth.jwt.admin.enabled}")
    private boolean enabled;

    @Value(value = "${auth.jwt.admin.email:user@example.com}")
    private String adminEmail;

    @Value("${auth.jwt.admin.password:password}")
    private String adminPassword;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(enabled){

            User user = new User();
            user.setEmail(adminEmail);
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setRoles(Set.of(Role.ADMIN.name()));

            userService.bootstrap(Arrays.asList(user));
        }
    }
}
