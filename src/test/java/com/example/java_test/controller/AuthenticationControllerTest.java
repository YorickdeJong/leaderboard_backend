package com.example.java_test.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.java_test.model.Users;
import com.example.java_test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

class AuthenticationControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        Users user = new Users();
        user.setName("testUser");
        user.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Assuming successful authentication

        var response = authenticationController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
    }

    @Test
    void testLogin_Failure() {
        Users user = new Users();
        user.setName("testUser");
        user.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid Credentials") {});

        var response = authenticationController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCreateAccount_Success() {
        Users newUser = new Users();
        newUser.setName("newUser");
        newUser.setPassword("newPassword");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Assuming successful authentication

        var response = authenticationController.createAccount(newUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
    }

    @Test
    void testLogin_WithNonExistentUser() {
        Users user = new Users();
        user.setName("nonExistentUser");
        user.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("User not found") {});

        var response = authenticationController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testLogin_WithIncorrectPassword() {
        Users user = new Users();
        user.setName("testUser");
        user.setPassword("incorrectPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid Credentials") {});

        var response = authenticationController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    void testCreateAccount_WithExistingUsername() {
        Users newUser = new Users();
        newUser.setName("existingUser");
        newUser.setPassword("password");

        // Assuming the user already exists
        when(userRepository.findByUsername(newUser.getName())).thenReturn(Optional.of(new Users()));

        var response = authenticationController.createAccount(newUser);

        // Assuming that an attempt to create an account with an existing username should fail
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Check for an appropriate error message or response body
    }

    @Test
    void testCreateAccount_WithInvalidData() {
        Users newUser = new Users();
        // Set invalid data for the new user
        newUser.setName("a"); // Empty name
        newUser.setPassword("password");

        // Mock the userRepository to simulate saving the user
        when(userRepository.save(any(Users.class))).thenReturn(newUser);

        // Mock the authenticationManager.authenticate(...) call
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getName()).thenReturn(newUser.getName());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid Credentials") {});

        var response = authenticationController.createAccount(newUser);

        // Assuming that an attempt to create an account with invalid data should fail
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        // Check for an appropriate error message or response body
    }

    // Additional test cases can be added as needed
}
