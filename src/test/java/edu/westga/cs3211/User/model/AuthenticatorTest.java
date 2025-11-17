package edu.westga.cs3211.User.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthenticatorTest {

    private Authenticator auth;

    @BeforeEach
    void setup() {
        auth = new Authenticator();
    }

    @Test
    void testConstructorAddsAllUsers() {
        Collection<User> users = auth.getAllUsers();

        assertEquals(4, users.size());

        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Haynes") && u.getRole() == UserRole.Crew));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("g") && u.getRole() == UserRole.Crew));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Brent") && u.getRole() == UserRole.Quartermaster));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("f") && u.getRole() == UserRole.Quartermaster));
    }

    @Test
    void testGetAllUsersReturnsCopyNotReference() {
        Collection<User> original = auth.getAllUsers();

        original.clear();

        assertEquals(4, auth.getAllUsers().size());
    }

    @Test
    void testGetUserRoleReturnsCorrectRole() {
        assertEquals(UserRole.Crew, auth.getUserRole("Haynes"));
        assertEquals(UserRole.Quartermaster, auth.getUserRole("Brent"));
    }

    @Test
    void testGetUserRoleReturnsNullIfNotFound() {
        assertNull(auth.getUserRole("NotAUser"));
    }

    @Test
    void testGetUserRoleThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> auth.getUserRole(null));
    }

    @Test
    void testVerifyReturnsTrueForValidCredentials() {
        assertTrue(auth.verify("Haynes", "CrewMate1"));
        assertTrue(auth.verify("Brent", "QMaster1"));
    }

    @Test
    void testVerifyReturnsFalseForWrongPassword() {
        assertFalse(auth.verify("Haynes", "WrongPassword"));
    }

    @Test
    void testVerifyReturnsFalseForWrongUsername() {
        assertFalse(auth.verify("NoSuchUser", "CrewMate1"));
    }

    @Test
    void testVerifyReturnsFalseForBothWrong() {
        assertFalse(auth.verify("X", "Y"));
    }

    @Test
    void testGetUserIfValidReturnsUserForValidCredentials() {
        User u = auth.getUserIfValid("Haynes", "CrewMate1");

        assertNotNull(u);
        assertEquals("Haynes", u.getName());
        assertEquals("CrewMate1", u.getPassword());
        assertEquals(UserRole.Crew, u.getRole());
    }

    @Test
    void testGetUserIfValidReturnsNullForInvalidCredentials() {
        assertNull(auth.getUserIfValid("Haynes", "BadPass"));
        assertNull(auth.getUserIfValid("WrongUser", "CrewMate1"));
    }
}
