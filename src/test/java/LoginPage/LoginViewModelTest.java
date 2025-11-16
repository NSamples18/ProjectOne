package LoginPage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import LoginPage.ViewModal.LoginViewModel;
import edu.westga.cs3211.User.model.Authenticator;

/**
 * Tests for the LoginViewModel.
 */
public class LoginViewModelTest {

    private LoginViewModel viewModel;

    @BeforeEach
    public void setUp() {
        this.viewModel = new LoginViewModel();
    }

    @Test
    public void testInitialStateHasEmptyFields() {
        assertEquals("", this.viewModel.usernameProperty().get());
        assertEquals("", this.viewModel.passwordProperty().get());
        assertFalse(this.viewModel.canLogin());
    }

    @Test
    public void testCanLoginWhenBothFieldsAreFilled() {
        this.viewModel.usernameProperty().set("Haynes");
        this.viewModel.passwordProperty().set("CrewMate1");

        assertTrue(this.viewModel.canLogin());
    }

    @Test
    public void testCanLoginFailsWhenUsernameEmpty() {
        this.viewModel.passwordProperty().set("CrewMate1");

        assertFalse(this.viewModel.canLogin());
    }

    @Test
    public void testCanLoginFailsWhenPasswordEmpty() {
        this.viewModel.usernameProperty().set("Haynes");

        assertFalse(this.viewModel.canLogin());
    }

    @Test
    public void testLoginSucceedsWithValidCredentials() {
        this.viewModel.usernameProperty().set("Haynes");
        this.viewModel.passwordProperty().set("CrewMate1");

        assertTrue(this.viewModel.login());
    }

    @Test
    public void testLoginFailsWithInvalidCredentials() {
        this.viewModel.usernameProperty().set("Haynes");
        this.viewModel.passwordProperty().set("Wrong");

        assertFalse(this.viewModel.login());
    }
}
