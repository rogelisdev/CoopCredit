package com.coopcredit.credit.application_service.application.service;

import com.coopcredit.credit.application_service.infrastructure.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    // ------------------- GENERATE ACCESS TOKEN ------------------
    public String generateToken(UserEntity userEntity){
        return buildToken(userEntity, jwtExpiration);
    }
    // ------------------- GENERATE REFRESH TOKEN ----
    public String generateRefreshToken(UserEntity userEntity){
        return buildToken(userEntity, refreshExpiration);
    }

    // ------------------- PRIVATE METHOD TO BUILD TOKEN --
    public String buildToken(final UserEntity user, final long expiration){
        return Jwts.builder()
                .setId(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("role", user.getRole().name())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();

    }

    // ------------------- OBTAIN SIGNING KEY ---
    public SecretKey getSignKey(){
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    // ------------------- EXTRACT USERNAME FROM TOKEN -------
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ------------------- VALIDATE TOKEN ----------
    public boolean isTokenValid(String token, UserEntity userEntity){
        final String username = extractUsername(token);
        return (username.equals(userEntity.getUsername())) && !isTokenExpired(token);
    }

    // ------------------- CHECK EXPIRATION -----
    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before((new Date()));
    }

}
