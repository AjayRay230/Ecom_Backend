package com.ajay.ecom_proj.service;

import io.jsonwebtoken.security.Keys;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Getter
public class KeyProvider {
    private final SecretKey secretKey;
    public KeyProvider(@Value("${jwt.secret}") String secret)
    { try {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    catch (Exception e) {
        throw new IllegalStateException("Failed to get Jwt key",e);
    }
    }
}
