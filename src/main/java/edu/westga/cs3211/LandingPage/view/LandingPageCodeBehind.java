package edu.westga.cs3211.LandingPage.view;

import edu.westga.cs3211.LandingPage.viewmodel.LandingPageViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
     * @precondition username != null
     * @postcondition greetingLabel shows "Hello, username!"
     */
    public void setGreeting(String username) {
        this.greetingLabel.setText("Hello, " + username + "!");
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
        System.out.println("Logout clicked");
        // TODO: Return to LoginPage
    }
}
