package com.example.java_test.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(GoogleController.class)
public class GoogleControllerTest {

    @MockBean
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @InjectMocks
    private GoogleController googleController;

    private MockMvc mockMvc;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(googleController).build();
    }

    @Test
    public void whenValidToken_thenReturnsTokens() throws Exception {
        String fakeIdToken = "fakeIdToken";
        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setEmail("test@example.com");
        GoogleIdToken idToken = new GoogleIdToken(null, payload, null, null);

        given(googleIdTokenVerifier.verify(fakeIdToken)).willReturn(idToken);

        mockMvc.perform(post("/api/auth/google")
                        .contentType("application/json")
                        .content("{\"access_token\":\"" + fakeIdToken + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'accessToken': 'someToken', 'refreshToken': 'someToken'}"));
    }

    @Test
    public void whenInvalidToken_thenUnauthorized() throws Exception {
        String fakeIdToken = "invalidToken";
        when(googleIdTokenVerifier.verify(fakeIdToken)).thenReturn(null);

        mockMvc.perform(post("/api/auth/google")
                        .contentType("application/json")
                        .content("{\"access_token\":\"" + fakeIdToken + "\"}"))
                .andExpect(status().isUnauthorized());
    }
}
