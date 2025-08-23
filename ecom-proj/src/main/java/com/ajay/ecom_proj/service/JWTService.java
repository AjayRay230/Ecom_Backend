package com.ajay.ecom_proj.service;

import com.ajay.ecom_proj.model.Product;
import com.ajay.ecom_proj.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private final KeyProvider keyProvider;
    public JWTService(KeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }
    public String generateToken(Users user) {
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("roles",user.getRole());
        claims.put("userId",user.getUserId());
        return    Jwts.builder().claims()
                .add(claims)
                .subject(user.getUserName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .and()
                .signWith( keyProvider.getSecretKey())
                .compact();
    }
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims  = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return  Jwts.parser()
                .verifyWith(keyProvider.getSecretKey())
                .build().parseClaimsJws(token).getPayload();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return  (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    private boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
