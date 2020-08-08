package com.tayfurunal.mentorship.security;

import com.tayfurunal.mentorship.domain.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {

    @Value("${app.auth.tokenSecret}")
    private String tokenSecret;

    @Value("${app.auth.jwtExpirationInMs}")
    private int jwtExpirationInMinute;

    public String createToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, jwtExpirationInMinute);
        Date date1MinutesAdded = calendar.getTime();

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setExpiration(date1MinutesAdded)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
