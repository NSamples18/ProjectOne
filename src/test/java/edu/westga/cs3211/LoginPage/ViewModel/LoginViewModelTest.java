package edu.westga.cs3211.LoginPage.ViewModel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.User.model.UserRole;

class LoginViewModelTest {

    private LoginViewModel vm;

    @BeforeEach
    void setup() {
        vm = new LoginViewModel();
    }

    @Test
    void testConstructorInitializesProperties() {
        assertNotNull(vm.usernameProperty());
        assertNotNull(vm.passwordProperty());
        assertNull(vm.getLoggedInUser());

        assertEquals("", vm.usernameProperty().get());
        assertEquals("", vm.passwordProperty().get());
    }

    @Test
    void testLoginSucceedsForValidCredentials() {
        vm.usernameProperty().set("Haynes");
        vm.passwordProperty().set("CrewMate1");

        boolean result = vm.login();

        assertTrue(result);
        assertNotNull(vm.getLoggedInUser());
        assertEquals("Haynes", vm.getLoggedInUser().getName());
        assertEquals(UserRole.Crew, vm.getLoggedInUser().getRole());
    }

    @Test
    void testLoginFailsForInvalidUsername() {
        vm.usernameProperty().set("DoesNotExist");
        vm.passwordProperty().set("whatever");

        boolean result = vm.login();

        assertFalse(result);
        assertNull(vm.getLoggedInUser());
    }

    @Test
    void testLoginFailsForWrongPassword() {
        vm.usernameProperty().set("Crewmate");
        vm.passwordProperty().set("wrongpass");

        boolean result = vm.login();

        assertFalse(result);
        assertNull(vm.getLoggedInUser());
    }

    @Test
    void testLoginFailsWhenUsernameEmpty() {
        vm.usernameProperty().set("");
        vm.passwordProperty().set("qm123");

        boolean result = vm.login();

        assertFalse(result);
        assertNull(vm.getLoggedInUser());
    }

    @Test
    void testLoginFailsWhenPasswordEmpty() {
        vm.usernameProperty().set("Quartermaster");
        vm.passwordProperty().set("");

        boolean result = vm.login();

        assertFalse(result);
        assertNull(vm.getLoggedInUser());
    }
}
