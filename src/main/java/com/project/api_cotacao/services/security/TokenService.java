package com.project.api_cotacao.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private static final String SECRET_KEY = "your_secret_key"; // Substitua por uma chave segura
    private static final long EXPIRATION_TIME = 10 * 60 * 60 * 1000; // 10 horas
    private static final String ISSUER = "api-cotacao"; // Nome da sua API

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public TokenService() {
        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
        this.verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
    }

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuer(ISSUER)
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getExpiresAt().after(new Date());
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}