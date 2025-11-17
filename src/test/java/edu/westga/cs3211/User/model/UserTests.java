package edu.westga.cs3211.User.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testConstructorCreatesValidUser() {
        User u = new User("Alice", "password123", UserRole.Crew);

        assertEquals("Alice", u.getName());
        assertEquals("password123", u.getPassword());
        assertEquals(UserRole.Crew, u.getRole());
    }

    @Test
    void testConstructorThrowsWhenNameNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new User(null, "pass", UserRole.Crew)
        );
    }

    @Test
    void testConstructorThrowsWhenPasswordNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Alice", null, UserRole.Crew)
        );
    }

    @Test
    void testConstructorThrowsWhenRoleNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Alice", "pass", null)
        );
    }

    @Test
    void testGettersReturnCorrectValues() {
        User u = new User("Bob", "mypwd", UserRole.Quartermaster);

        assertEquals("Bob", u.getName());
        assertEquals("mypwd", u.getPassword());
        assertEquals(UserRole.Quartermaster, u.getRole());
    }

    @Test
    void testToStringReturnsName() {
        User u = new User("Charlie", "pw", UserRole.Crew);

        assertEquals("Charlie", u.toString());
    }
}
