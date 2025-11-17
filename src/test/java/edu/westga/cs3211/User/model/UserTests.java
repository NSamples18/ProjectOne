package edu.westga.cs3211.User.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;

class UserTests {

    @Test
    void testValidConstructor() {
        User user = new User("John", "pass123", UserRole.Crew);

        assertEquals("John", user.getName());
        assertEquals("pass123", user.getPassword());
        assertEquals(UserRole.Crew, user.getRole());
    }

    @Test
    void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "pass123", UserRole.Crew);
        });
    }

    @Test
    void testConstructorNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("John", null, UserRole.Crew);
        });
    }

    @Test
    void testConstructorNullRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("John", "pass123", null);
        });
    }
}

