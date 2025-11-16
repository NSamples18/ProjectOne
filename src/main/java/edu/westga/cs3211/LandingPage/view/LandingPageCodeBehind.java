package edu.westga.cs3211.LandingPage.view;

import java.io.IOException;

import edu.westga.cs3211.LandingPage.viewmodel.LandingPageViewModel;
import edu.westga.cs3211.User.model.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the Landing Page.
 * Manages greeting text and navigation actions.
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class LandingPageCodeBehind {

    @FXML
    private Label greetingLabel;

    @FXML
    private Button addStockButton;

    @FXML
    private Button viewStockButton;

    @FXML
    private Button logoutButton;

    private final LandingPageViewModel viewModel;

    /**
     * Creates the Landing Page controller.
     * 
     * @precondition none
     * @postcondition viewModel is initialized
     */
    public LandingPageCodeBehind() {
        this.viewModel = new LandingPageViewModel();
    }

    /**
     * Sets the greeting text using the logged-in user’s name.
     * 
     * @param username the username of the user.
     * @precondition username != null
     * @postcondition greetingLabel shows "Hello, username!"
     */
    public void setGreeting(String username) {
        this.greetingLabel.setText("Hello, " + username + "!");
    }
    
    /**
     * Makes it so the quatermaster see's the view stock button.
     * @param role the role of the user.
     */
    public void configureForRole(UserRole role) {
        if (role == UserRole.Crew) {
            this.viewStockButton.setVisible(false);
        } else if (role == UserRole.Quartermaster) {
            this.viewStockButton.setVisible(true);
        }
    }


    /**
     * Initializes the controller after the FXML is loaded.
     */
    @FXML
    private void initialize() {
        // No bindings yet — add later when AddStockPage & ViewStockPage are built
    }

    @FXML
    private void handleAddStock() {
        System.out.println("Add Stock clicked");
        // TODO: Navigate to AddStockPage.fxml
    }

    @FXML
    private void handleViewStock() {
        System.out.println("View Stock clicked");
        // TODO: Navigate to ViewStockPage.fxml
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LoginPage.fxml")
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage window = (Stage) this.greetingLabel.getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Login");
            window.show();

        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

}
