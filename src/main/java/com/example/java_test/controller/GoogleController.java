package com.example.java_test.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RequestMapping("/api/auth/google")

public class GoogleController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @PostMapping
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody Map<String, String> body) {
        try {
            String googleIdTokenStr = body.get("access_token");
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(googleIdTokenStr);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Create access and refresh tokens for your application
                String accessToken = createToken(payload, "secret", 10); // 10 minutes for access token
                String refreshToken = createToken(payload, "refreshSecret", 60 * 24); // 1 day for refresh token

                return ResponseEntity.ok(Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken
                ));
            } else {
                return ResponseEntity.status(401).body("Invalid ID token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during token verification");
        }
    }

    private String createToken(GoogleIdToken.Payload payload, String secret, int minutes) {
        return JWT.create()
                .withSubject(payload.getEmail()) // or another unique identifier from the payload
                .withExpiresAt(new Date(System.currentTimeMillis() + (long) minutes * 60 * 1000))
                .sign(Algorithm.HMAC256(secret));
    }
}
