package com.serkanalgl.jwt.starter.common.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
public class MongoDBConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new MongoDBAuditing();
    }

}
