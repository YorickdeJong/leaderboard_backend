package com.example.java_test.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.java_test.model.Users;
import com.example.java_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
            );
            String accessToken = generateAccessToken(authentication);
            String refreshToken = generateRefreshToken(authentication);
            Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
            return ResponseEntity.ok().body(tokens);
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid Credentials"));
        }
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody Users newUser) {
        String rawPassword = newUser.getPassword(); // Store the raw password
        newUser.setPassword(passwordEncoder.encode(rawPassword)); // Encode the password
        userRepository.save(newUser);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(newUser.getName(), rawPassword) // Use the raw password for authentication
            );
            String accessToken = generateAccessToken(authentication);
            String refreshToken = generateRefreshToken(authentication);
            Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(tokens);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid Credentials"));
        }
    }

    private String generateAccessToken(Authentication authentication) {
        final long EXPIRATION_TIME = 864_000_00; // 1 day in milliseconds
        final String SECRET = "YourAccessTokenSecret"; // Replace with a real secret key for access token

        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    private String generateRefreshToken(Authentication authentication) {
        final long REFRESH_EXPIRATION_TIME = 7 * 864_000_00; // 7 days in milliseconds
        final String SECRET = "YourRefreshTokenSecret"; // Replace with a real secret key for refresh token

        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}