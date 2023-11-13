package com.example.java_test.integration_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAccount_WithInvalidData() throws Exception {
        // Create a JSON string with invalid user data
        String userJson = "{\"name\":\"a\", \"password\":\"password\"}";

        mockMvc.perform(post("/api/auth/create-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()) // Changed to expect bad request due to validation failure
                .andExpect(jsonPath("$.username").value("Name must be at least 3 characters long")); // Assuming this is your validation message
    }
}
