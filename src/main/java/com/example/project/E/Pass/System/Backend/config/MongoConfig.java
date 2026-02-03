package com.example.project.E.Pass.System.Backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient(Environment environment) {
        String uri = environment.getProperty("spring.mongodb.uri");
        if (uri == null || uri.isBlank()) {
            uri = environment.getProperty("spring.data.mongodb.uri");
        }
        if (uri == null || uri.isBlank()) {
            throw new IllegalStateException("MongoDB URI is not configured. Set 'spring.mongodb.uri' (or 'spring.data.mongodb.uri').");
        }
        return MongoClients.create(uri);
    }

}
