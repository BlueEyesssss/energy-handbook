package com.khaphp.energyhandbook.Util.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;


@Component
@Slf4j
public class JwtHelper {
    @Value("${security.secret_key}")
    private String secretKey;

    @Value("${security.time_expired}")
    private int expiredTime;

    public String generateToken(String username, Map<String, ?> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .signWith(getSecretKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredTime))
                .compact();
    }

    public SecretKey getSecretKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) { //vừa check username lu7uu và check time expired
        if (token == null || userDetails == null) {
            return false;
        }
        return getUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpiredTime(token).before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date getExpiredTime(String token) {
        return getClaims(token)
                .getExpiration();
    }

    public String getUsername(String token) {
        return getClaims(token)
                .getSubject();
    }
}
