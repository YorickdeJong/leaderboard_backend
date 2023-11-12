package com.example.java_test_real.model;
import com.example.java_test.model.Users;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Userstest {
    @Test
    void testUserGettersAndSetters() {
        Long id = 1L;
        String username = "TestUser";
        String password = "TestPassword";
        String email = "test@example.com";

        Users user = new Users();

        user.setId(id);
        user.setName(username);
        user.setPassword(password);
        user.setEmail(email);

        assertEquals(id, user.getId());
        assertEquals(username, user.getName());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testUserConstructor() {
        Long id = 1L; // Note: This won't be set through constructor
        String username = "TestUser";
        String password = "TestPassword";
        String email = "test@example.com";

        Users user = new Users(username, password, email);

        assertNull(user.getId()); // ID is not set through the constructor
        assertEquals(username, user.getName());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
    }
}
