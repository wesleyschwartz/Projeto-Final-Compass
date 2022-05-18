package com.shopstyle.customer.config.security;

import com.shopstyle.customer.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenService {
    @Value("${jwt.security.expiration}")
    private String expiration;
    @Value("${jwt.security.key}")
    private String key;

    public String generateToken(Authentication authentication) {
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setIssuer("customer")
                .setSubject(principal.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean isTokenValid(String token) {
         try {
             Jwts.parser().setSigningKey(this.key).parseClaimsJws(token);
             return true;
         }catch (Exception e){
             return false;
         }
    }

    public Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }
}
