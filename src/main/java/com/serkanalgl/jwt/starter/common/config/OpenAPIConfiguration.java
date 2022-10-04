package com.serkanalgl.jwt.starter.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =@Info(
                title = "Spring Boot JWT Starter API",
                version = "${api.version}",
                contact = @Contact(
                        name = "Serkan Algül", email = "serkanalgl@gmail.com"
                ),
                description = "${api.description}"
        )
)

@SecurityScheme(
        name = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Provide the JWT token. JWT token can be obtained from the login endpoint"
)
@Configuration
public class OpenAPIConfiguration {
}
