package edu.westga.cs3211.AddStock.view;

import java.util.List;

import edu.westga.cs3211.AddStock.viewmodel.AddStockViewModel;
import edu.westga.cs3211.LandingPage.view.LandingPageCodeBehind;
import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.ProjectOne.Main;
import edu.westga.cs3211.User.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the Add Stock page.
 * Connects the FXML view to the AddStockViewModel and handles user actions.
 * @author ns00184
 * @version 2025
 */
public class AddStockController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField sizeField;

    @FXML
    private ComboBox<SpecialQualities> specialCombo;

    @FXML
    private ComboBox<Condition> conditionCombo;

    @FXML
    private DatePicker expirationDatePicker;

    @FXML
    private Button submitButton;

    private AddStockViewModel viewModel;
    private User currentUser;

    /**
     * Initializes the controller with the current user.
     *
     * @param user the logged-in user
     */
    public void init(User user) {
        this.currentUser = user;
        this.viewModel = new AddStockViewModel();
        this.viewModel.setCurrentUser(user);

        this.nameField.textProperty().bindBidirectional(this.viewModel.nameProperty());
        this.sizeField.textProperty().bindBidirectional(this.viewModel.sizeProperty());
        this.specialCombo.valueProperty().bindBidirectional(this.viewModel.specialProperty());
        this.conditionCombo.valueProperty().bindBidirectional(this.viewModel.conditionProperty());
        this.expirationDatePicker.valueProperty().bindBidirectional(this.viewModel.expirationProperty());
        this.submitButton.disableProperty().bind(this.viewModel.canSubmitProperty().not());
    }

    
    @FXML
    public void initialize() {
        this.specialCombo.getItems().setAll(SpecialQualities.values());
        this.conditionCombo.getItems().setAll(Condition.values());
    }

    /**
     * Handles submitting the stock to the system.
     */
    @FXML
    private void handleSubmit() {
        Stock stock = this.viewModel.buildStockOrNull();
        if (stock == null) {
            this.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please check all fields.");
            return;
        }

        List<Compartment> compatible = this.viewModel.findCompatibleCompartments(stock);
        if (compatible.isEmpty()) {
            this.showAlert(Alert.AlertType.WARNING, "No Compatible Compartment",
                "No compartment can store this stock.");
            return;
        }

        Compartment selected = compatible.get(0);
        this.viewModel.addStockToCompartment(stock, selected);

        this.showAlert(Alert.AlertType.INFORMATION, "Success", "Stock successfully added.");

        this.resetFields();
    }

	private void resetFields() {
		this.nameField.clear();
        this.sizeField.clear();
        this.specialCombo.getSelectionModel().clearSelection();
        this.conditionCombo.getSelectionModel().clearSelection();
        this.expirationDatePicker.setValue(null);
	}

    /**
     * Handles navigation to the landing page.
     */
    @FXML
    private void handleHome() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LandingPage.fxml"));
            Parent root = loader.load();
            LandingPageCodeBehind controller = loader.getController();
            controller.init(this.currentUser);
            Stage stage = (Stage) this.nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Landing Page");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles reviewing stock changes.
     */
    @FXML
    private void handleReviewStock() {
        this.showAlert(Alert.AlertType.INFORMATION,
            "Unavailable",
            "The review stock page has not been implemented yet.");
    }

    /**
     * Handles logging out of the system.
     */
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LoginPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) this.nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert dialog.
     *
     * @param type the alert type
     * @param title the alert title
     * @param message the alert message
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
