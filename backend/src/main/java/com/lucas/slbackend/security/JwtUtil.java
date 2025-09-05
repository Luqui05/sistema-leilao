package com.lucas.slbackend.security;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

// Classe destinada a manipular tokens JWT
@Component
public class JwtUtil {

  private final Algorithm alg;
  private final JWTVerifier verifier;
  private final long expirationMillis;

  public JwtUtil(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration-millis}") long expirationMillis) {
    this.alg = Algorithm.HMAC256(secret);
    this.verifier = JWT.require(alg).build();
    this.expirationMillis = expirationMillis;
  }

  public String generateToken(String subjectEmail, List<String> roles) {
    long now = System.currentTimeMillis();
    Date expiresAt = new Date(now + expirationMillis);
    return JWT.create()
        .withSubject(subjectEmail)
        .withArrayClaim("roles", roles.toArray(String[]::new))
        .withIssuedAt(new Date(now))
        .withExpiresAt(expiresAt)
        .sign(alg);
  }

  public boolean isValid(String token) {
    try {
      verifier.verify(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getSubject(String token) {
    DecodedJWT jwt = verifier.verify(token);
    return jwt.getSubject();
  }

  public List<String> getRoles(String token) {
    DecodedJWT jwt = verifier.verify(token);
    return jwt.getClaim("roles").asList(String.class);
  }

  public long getExpirationMillis() {
    return expirationMillis;
  }
}
