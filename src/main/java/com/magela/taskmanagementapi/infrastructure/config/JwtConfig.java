package com.magela.taskmanagementapi.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Value("${api.security.token.expiration-in-minutes}")
    private int expirationInMinutes;

    public String getSecretKey() {
        return secretKey;
    }

    public int getExpirationInMinutes() {
        return expirationInMinutes;
    }
}
