package com.example.project.E.Pass.System.Backend;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class EPassSystemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EPassSystemBackendApplication.class, args);
    }

    @Bean
    ApplicationRunner mongoConnectionInfo(MongoClient mongoClient, MongoTemplate mongoTemplate, Environment environment) {
        return args -> {
            String rawUri = environment.getProperty("spring.data.mongodb.uri");
            String maskedUri = rawUri == null ? null : rawUri.replaceAll("(mongodb(?:\\+srv)?://[^:]+:)([^@]+)(@)", "$1***$3");
            String rawBootUri = environment.getProperty("spring.mongodb.uri");
            String maskedBootUri = rawBootUri == null ? null : rawBootUri.replaceAll("(mongodb(?:\\+srv)?://[^:]+:)([^@]+)(@)", "$1***$3");
            System.out.println("spring.profiles.active: " + environment.getProperty("spring.profiles.active"));
            System.out.println("spring.data.mongodb.uri: " + maskedUri);
            System.out.println("spring.mongodb.uri: " + maskedBootUri);
            System.out.println("spring.data.mongodb.host: " + environment.getProperty("spring.data.mongodb.host"));
            System.out.println("spring.data.mongodb.port: " + environment.getProperty("spring.data.mongodb.port"));
            System.out.println("spring.data.mongodb.database: " + environment.getProperty("spring.data.mongodb.database"));
            System.out.println("spring.mongodb.host: " + environment.getProperty("spring.mongodb.host"));
            System.out.println("spring.mongodb.port: " + environment.getProperty("spring.mongodb.port"));
            System.out.println("spring.mongodb.database: " + environment.getProperty("spring.mongodb.database"));

            List<ServerAddress> hosts = mongoClient.getClusterDescription()
                    .getClusterSettings()
                    .getHosts();
            String hostList = hosts.stream()
                    .map(ServerAddress::toString)
                    .collect(Collectors.joining(", "));
            System.out.println("MongoDB connected hosts: " + hostList);
            System.out.println("MongoDB database: " + mongoTemplate.getDb().getName());
        };
    }
}
