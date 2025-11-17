package LoginPage.View;

import java.io.IOException;

import LoginPage.ViewModal.LoginViewModel;
import edu.westga.cs3211.LandingPage.view.LandingPageCodeBehind;
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
 * Clean MVVM version.
 * @author ns00184
 * @version 2025
 */
public class LoginPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private LoginViewModel viewModel;

    /**
     * Initialize bindings to ViewModel.
     */
    @FXML
    public void initialize() {
        this.viewModel = new LoginViewModel();

        this.usernameField.textProperty().bindBidirectional(this.viewModel.usernameProperty());
        this.passwordField.textProperty().bindBidirectional(this.viewModel.passwordProperty());

        this.loginButton.disableProperty().bind(
            this.viewModel.usernameProperty().isEmpty()
                .or(this.viewModel.passwordProperty().isEmpty())
        );
    }

    /**
     * Handles login clicked.
     */
    @FXML
    private void handleLogin() {
        try {
            if (this.viewModel.login()) {

                FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LandingPage.fxml")
                );

                Parent root = loader.load();

                LandingPageCodeBehind controller = loader.getController();
                controller.init(
                    this.viewModel.loggedInUsernameProperty().get(),
                    this.viewModel.loggedInRoleProperty().get()
                );

                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Landing Page");
                stage.show();

            } else {
                System.out.println("Invalid login.");
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}
