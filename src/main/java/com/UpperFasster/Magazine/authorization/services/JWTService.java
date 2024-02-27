package com.UpperFasster.Magazine.authorization.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JWTService {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    public String createAccessToken(UUID token_id, UUID user_id, UUID rti) { // , , int exp_epoch
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        Instant utcInstant = Instant.now();
        Map<String, Object> customHeaders = new HashMap<>();
        customHeaders.put("tkn", "access");

        return JWT.create()
                .withHeader(customHeaders)
                .withAudience(rti.toString())
                .withSubject(user_id.toString())  // for who this token
                .withJWTId(token_id.toString())  // token id
                .withIssuedAt(Date.from(utcInstant))  // when
                .withExpiresAt(Date.from(utcInstant.plus(10, ChronoUnit.SECONDS)))  // expiration time
                .sign(algorithm);
    }

    public String createRefreshToken(UUID token_id, UUID user_id) {
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        Instant utcInstant = Instant.now();

        Map<String, Object> customHeaders = new HashMap<>();
        customHeaders.put("tkn", "refresh");

        return JWT.create()
                .withHeader(customHeaders)
                .withSubject(user_id.toString())  // for who this token
                .withJWTId(token_id.toString())  // token id
                .withIssuedAt(Date.from(utcInstant))  // when
                .withExpiresAt(Date.from(utcInstant.plus(30, ChronoUnit.DAYS)))  // expiration time
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            verifier.verify(token);

            return true;
        } catch (TokenExpiredException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getTokenFromBearer(String token) {
        return token.substring(7);
    }

    public DecodedJWT getPayloadFromToken(String token) {
        return JWT.decode(token);
    }

}
