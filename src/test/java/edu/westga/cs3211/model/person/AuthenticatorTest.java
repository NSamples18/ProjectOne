package edu.westga.cs3211.model.person;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.User.model.Authenticator;

class AuthenticatorTests {

    @Test
    void testVerifyHaynesValidCredentials() {
        Authenticator auth = new Authenticator();
        assertTrue(auth.verify("Haynes", "CrewMate1"));
    }

    @Test
    void testVerifyBrentValidCredentials() {
        Authenticator auth = new Authenticator();
        assertTrue(auth.verify("Brent", "QMaster1"));
    }

    @Test
    void testVerifyWrongPassword() {
        Authenticator auth = new Authenticator();
        assertFalse(auth.verify("Haynes", "WrongPassword"));
    }

    @Test
    void testVerifyWrongUsername() {
        Authenticator auth = new Authenticator();
        assertFalse(auth.verify("WrongUser", "CrewMate1"));
    }

    @Test
    void testVerifyWrongUserAndPassword() {
        Authenticator auth = new Authenticator();
        assertFalse(auth.verify("NotReal", "Nope"));
    }

    @Test
    void testVerifyIsCaseSensitive() {
        Authenticator auth = new Authenticator();
        assertFalse(auth.verify("haynes", "crewmate1"));   // lowercase should fail
    }
}

