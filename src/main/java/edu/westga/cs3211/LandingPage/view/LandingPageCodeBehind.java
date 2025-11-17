package edu.westga.cs3211.LandingPage.view;

import java.io.IOException;

import edu.westga.cs3211.AddStock.view.AddStockController;
import edu.westga.cs3211.LandingPage.viewmodel.LandingPageViewModel;
import edu.westga.cs3211.User.model.User;
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

    private User currentUser;

    private LandingPageViewModel viewModel;

    public LandingPageCodeBehind() {
        this.viewModel = new LandingPageViewModel();
    }

    @FXML
    private void initialize() {
        this.greetingLabel.textProperty().bind(this.viewModel.greetingProperty());
        this.viewStockButton.visibleProperty().bind(this.viewModel.viewStockVisibleProperty());
    }

    /**
     * Called by Login controller immediately after loading the page.
     */
    public void init(User user) {
        this.viewModel = new LandingPageViewModel();
        this.viewModel.initialize(user.getName(), user.getRole());
        this.greetingLabel.textProperty().bind(this.viewModel.greetingProperty());
        this.viewStockButton.visibleProperty().bind(this.viewModel.viewStockVisibleProperty());
        this.currentUser = user;
    }

    @FXML
    private void handleAddStock() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/AddStockPage.fxml"));
            Parent root = loader.load();

            AddStockController controller = loader.getController();
            controller.init(this.currentUser);

            Stage stage = (Stage) this.addStockButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Stock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleViewStock() {
        System.out.println("View Stock clicked");
    }

    @FXML
    private void handleLogout() {
        this.loadPage("/edu/westga/cs3211/ProjectOne/view/LoginPage.fxml", "Login");
    }

    private void loadPage(String fullPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fullPath));
            Parent root = loader.load();

            Stage stage = (Stage) this.logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}
