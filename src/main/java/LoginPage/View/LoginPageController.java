package LoginPage.View;

import java.io.IOException;

import LoginPage.ViewModal.LoginViewModel;
import edu.westga.cs3211.LandingPage.view.LandingPageCodeBehind;
import edu.westga.cs3211.User.model.UserRole;
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
        String username = this.viewModel.getUserName();

        if (success) {
        	UserRole role = this.viewModel.getUserRole();
        	goToLandingPage(username, role);
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private void goToLandingPage(String username, UserRole role) {
    	try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LandingPage.fxml")
            );

            Parent root = loader.load();

            LandingPageCodeBehind controller = loader.getController();
            controller.setGreeting(username);
            controller.configureForRole(role);

            Scene scene = new Scene(root);

            Stage window = (Stage) loginBtn.getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Landing Page");
            window.show();

        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

}

