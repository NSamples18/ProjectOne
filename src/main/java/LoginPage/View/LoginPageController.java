package LoginPage.View;

import java.io.IOException;

import LoginPage.ViewModal.LoginViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the Login Page.
 * @author ns00184
 * @version 2025
 */
public class LoginPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    private LoginViewModel viewModel;

    /**
     * Initializes the controller.
     * Sets up bindings to the ViewModel.
     */
    public void initialize() {
        this.viewModel = new LoginViewModel();

        this.usernameField.textProperty().bindBidirectional(this.viewModel.usernameProperty());
        this.passwordField.textProperty().bindBidirectional(this.viewModel.passwordProperty());

        this.loginBtn.disableProperty().bind(
            this.usernameField.textProperty().isEmpty()
                .or(this.passwordField.textProperty().isEmpty())
        );
    }

    @FXML
    private void handleLogin() {

        boolean success = this.viewModel.login();

        if (success) {
            this.goToLandingPage();
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private void goToLandingPage() {
        try {
            Parent root = FXMLLoader.load(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LandingPage.fxml")
            );

            Scene scene = new Scene(root);

            Stage window = (Stage) this.loginBtn.getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Landing Page");
            window.show();

        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}

