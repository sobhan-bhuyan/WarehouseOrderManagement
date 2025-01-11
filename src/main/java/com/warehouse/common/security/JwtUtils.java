package com.warehouse.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.warehouse.exceptions.UnauthorisedException;

import java.util.Date;

public class JwtUtils {

    public static String createToken(String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("client1")
                .withClaim("name", "client1")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
                .sign(algorithm);

    }

    public static void verifyToken(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("sobhan")
                .acceptExpiresAt(10000)
                .build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new UnauthorisedException("JWT is not valid", e);
        }
    }
}
