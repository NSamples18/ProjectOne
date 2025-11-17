package edu.westga.cs3211.AddStock.view;

import java.io.IOException;

import edu.westga.cs3211.AddStock.viewmodel.AddStockViewModel;
import edu.westga.cs3211.LandingPage.modal.Condition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the Add Stock page.
 */
public class AddStockController {

    @FXML private Button homeButton;
    @FXML private Button reviewButton;
    @FXML private Button logoutButton;

    @FXML private TextField nameField;
    @FXML private TextField sizeField;
    @FXML private TextField specialQualsField;
    @FXML private ComboBox<Condition> conditionCombo;
    @FXML private DatePicker expirationDatePicker;

    @FXML private Button submitButton;

    private AddStockViewModel viewModel;

    @FXML
    public void initialize() {
        this.viewModel = new AddStockViewModel();

        this.conditionCombo.getItems().setAll(Condition.values());

        this.nameField.textProperty().bindBidirectional(this.viewModel.nameProperty());
        this.sizeField.textProperty().bindBidirectional(this.viewModel.sizeProperty());
        this.specialQualsField.textProperty().bindBidirectional(this.viewModel.specialProperty());
        this.expirationDatePicker.valueProperty().bindBidirectional(this.viewModel.expirationProperty());
        this.conditionCombo.valueProperty().bindBidirectional(this.viewModel.conditionProperty());

        this.submitButton.disableProperty().bind(this.viewModel.canSubmitProperty().not());
    }

    @FXML
    private void handleHome() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LandingPage.fxml")
            );

            Parent root = loader.load();
            Stage stage = (Stage) this.submitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Landing Page");
            stage.show();

        } catch (IOException exp) {
            System.err.println("ERROR: Could not load LandingPage.fxml");
            exp.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LoginPage.fxml")
            );

            Parent root = loader.load();
            Stage stage = (Stage) this.submitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

        } catch (IOException exp) {
            System.err.println("ERROR: Could not load LoginPage.fxml");
            exp.printStackTrace();
        }
    }

    @FXML
    private void handleReviewStock() {
        System.out.println("Navigate to Review Stock Page.");
        // TODO: Implement later
    }

    @FXML
    private void handleSubmit() {
        boolean success = this.viewModel.submitStock();

        if (success) {
            System.out.println("Stock successfully added.");
        } else {
            System.out.println("Error: Could not add stock.");
        }
    }
}
