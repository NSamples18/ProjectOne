package edu.westga.cs3211.model.person;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.User.model.Authenticator;

/**
 * Tests for the Authenticator class.
 */
public class AuthenticatorTest {

    private Authenticator authenticator;

    @BeforeEach
    public void setUp() {
        this.authenticator = new Authenticator();
    }

    @Test
    public void testVerifyWithValidCredentials() {
        assertTrue(this.authenticator.verify("Haynes", "CrewMate1"));
        assertTrue(this.authenticator.verify("Brent", "QMaster1"));
    }

    @Test
    public void testVerifyWithInvalidUsername() {
        assertFalse(this.authenticator.verify("Unknown", "CrewMate1"));
    }

    @Test
    public void testVerifyWithInvalidPassword() {
        assertFalse(this.authenticator.verify("Haynes", "WrongPass"));
    }

    @Test
    public void testVerifyWithNullInputs() {
        assertFalse(this.authenticator.verify(null, null));
        assertFalse(this.authenticator.verify("Haynes", null));
        assertFalse(this.authenticator.verify(null, "CrewMate1"));
    }
}
