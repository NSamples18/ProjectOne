package edu.westga.cs3211.AddStock.view;

import java.util.List;

import edu.westga.cs3211.AddStock.viewmodel.AddStockViewModel;
import edu.westga.cs3211.LandingPage.view.LandingPageCodeBehind;
import edu.westga.cs3211.ReviewStock.view.ReviewStockController;
import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;
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
    
    @FXML
    private Button reviewButton;
    
    @FXML
    private ComboBox<Compartment> compartmentCombo;

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
        this.compartmentCombo.itemsProperty().set(this.viewModel.getAvailableCompartments());
        this.compartmentCombo.valueProperty().bindBidirectional(this.viewModel.selectedCompartmentProperty());
        this.submitButton.disableProperty().bind(this.viewModel.canSubmitProperty().not());
        
        this.checkUserRole(user);
    } 

	private void checkUserRole(User user) {
		boolean isQuartermaster = user.getRole() == UserRole.Quartermaster;
        this.reviewButton.setVisible(isQuartermaster);
	}
    
	/**
	 * initializes the addstock controler.
	 */
	@FXML
    public void initialize() {
        this.specialCombo.getItems().setAll(SpecialQualities.values());
        this.conditionCombo.getItems().setAll(Condition.values());
    }

    @FXML
    private void handleSubmit() {
        Stock stock = this.viewModel.buildStockOrNull();
        if (!this.isValidStock(stock)) {
            this.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please check all fields.");
            return;
        }

        List<Compartment> compatible = this.viewModel.findCompatibleCompartments(stock);
        if (compatible.isEmpty()) {
            this.showAlert(Alert.AlertType.WARNING, "No Compatible Compartment",
                    "No compartment can store this stock.");
            return;
        }

        if (!this.hasUserSelectedCompartment()) {
            this.prepareCompartmentSelection(compatible);
            return;
        }

        Compartment selected = this.compartmentCombo.getValue();
        if (!compatible.contains(selected)) {
            this.prepareCompartmentSelection(compatible);
            return;
        }

        this.viewModel.addStockToCompartment(stock, selected);
        this.showAlert(Alert.AlertType.INFORMATION, "Success", "Stock successfully added.");

        this.resetFields();
        this.compartmentCombo.getItems().clear();
    }
    
    private boolean isValidStock(Stock stock) {
        return stock != null;
    }

    private boolean hasUserSelectedCompartment() {
        return this.compartmentCombo.getValue() != null;
    }

    private void prepareCompartmentSelection(List<Compartment> compatible) {
        this.compartmentCombo.getItems().setAll(compatible);
        this.showAlert(Alert.AlertType.INFORMATION, 
                "Select Compartment", 
                "Please select a storage compartment from the list, then click Submit again.");
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles reviewing stock changes.
     */
    @FXML
    private void handleReviewStock() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/ReviewStockPage.fxml")
            );

            Parent root = loader.load();

            ReviewStockController controller = loader.getController();
            controller.init(this.currentUser);

            Stage stage = (Stage) this.nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Review Stock Changes");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            this.showAlert(Alert.AlertType.ERROR,
                "Navigation Error",
                "Unable to open Review Stock page.");
        }
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
        } catch (Exception ex) {
            ex.printStackTrace();
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
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
