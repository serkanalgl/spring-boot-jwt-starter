package com.serkanalgl.jwt.starter.common.config.mongo;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class MongoDBAuditing implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return Optional.of(authentication.getName());
        }

        return Optional.of("anonymous");
    }
}
