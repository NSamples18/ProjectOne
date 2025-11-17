package LoginPage;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.LoginPage.ViewModel.LoginViewModel;

class LoginViewModelTests {

    @Test
    void testInitialValuesAreEmpty() {
        LoginViewModel vm = new LoginViewModel();

        assertEquals("", vm.usernameProperty().get());
        assertEquals("", vm.passwordProperty().get());
    }

//    @Test
//    void testCanLoginTrueForFilledFields() {
//        LoginViewModel vm = new LoginViewModel();
//
//        vm.usernameProperty().set("test");
//        vm.passwordProperty().set("123");
//
//        assertTrue(vm.canLogin());
//    }

//    @Test
//    void testCanLoginFalseForEmptyFields() {
//        LoginViewModel vm = new LoginViewModel();
//
//        vm.usernameProperty().set("");
//        vm.passwordProperty().set("");
//
//        assertFalse(vm.canLogin());
//    }

    @Test
    void testLoginSuccess() {
        LoginViewModel vm = new LoginViewModel();

        vm.usernameProperty().set("Haynes");
        vm.passwordProperty().set("CrewMate1");

        assertTrue(vm.login());
    }

    @Test
    void testLoginFailure() {
        LoginViewModel vm = new LoginViewModel();

        vm.usernameProperty().set("BadUser");
        vm.passwordProperty().set("WrongPass");

        assertFalse(vm.login());
    }
}

