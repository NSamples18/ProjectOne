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

public class LandingPageCodeBehind {

    @FXML
    private Label greetingLabel;

    @FXML
    private Button addStockButton;

    @FXML
    private Button viewStockButton;

    @FXML
    private Button logoutButton;

    private LandingPageViewModel viewModel;

    public LandingPageCodeBehind() {
        this.viewModel = new LandingPageViewModel();
    }

    @FXML
    private void initialize() {
        // Bind UI → ViewModel
        this.greetingLabel.textProperty().bind(this.viewModel.greetingProperty());
        this.viewStockButton.visibleProperty().bind(this.viewModel.viewStockVisibleProperty());
    }

    /**
     * Called by Login controller immediately after loading the page.
     */
    public void init(String username, UserRole role) {
        this.viewModel.initialize(username, role);
    }

    @FXML
    private void handleAddStock() {
        loadPage("/edu/westga/cs3211/ProjectOne/view/AddStockPage.fxml", "Add Stock");
    }

    @FXML
    private void handleViewStock() {
        System.out.println("View Stock clicked");
    }

    @FXML
    private void handleLogout() {
        loadPage("/LoginPage/View/LoginPage.fxml", "Login");
    }

    private void loadPage(String path, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Stage stage = (Stage) this.addStockButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}
