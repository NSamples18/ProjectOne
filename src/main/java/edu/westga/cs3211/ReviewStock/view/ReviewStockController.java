package edu.westga.cs3211.ReviewStock.view;

import edu.westga.cs3211.ReviewStock.viewmodel.ReviewStockViewModel;
import edu.westga.cs3211.inventory.StockChange;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.Authenticator;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.view.LandingPageCodeBehind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

/**
 * Controller for the Review Stock page.
 */
public class ReviewStockController {

    @FXML private TableView<StockChange> changesTable;

    @FXML private TableColumn<StockChange, String> userCol;
    @FXML private TableColumn<StockChange, String> stockCol;
    @FXML private TableColumn<StockChange, String> compartmentCol;
    @FXML private TableColumn<StockChange, String> remainingCol;
    @FXML private TableColumn<StockChange, String> timeCol;

    @FXML private ComboBox<User> crewFilter;
    @FXML private ComboBox<SpecialQualities> specialFilter;
    @FXML private DatePicker startDateFilter;
    @FXML private DatePicker endDateFilter;

    private ReviewStockViewModel viewModel;
    private User currentUser;

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Initialize controller after user is passed in.
     */
    public void init(User user) {
        this.currentUser = user;
        this.viewModel = new ReviewStockViewModel();

        this.changesTable.setItems(this.viewModel.getDisplayedChanges());
        this.initializeColumns();
        this.populateFilters();
        this.bindFilters();
    }

    /**
     * Sets up all table columns for StockChange fields.
     *
     * @postcondition All table columns display correct data
     */
    private void initializeColumns() {

        this.userCol.setCellValueFactory(sc ->
                new SimpleStringProperty(sc.getValue().getUser().getName()));

        this.stockCol.setCellValueFactory(sc ->
                new SimpleStringProperty(sc.getValue().getStock().getName()));

        this.compartmentCol.setCellValueFactory(sc ->
                new SimpleStringProperty(sc.getValue().getCompartment().getSpecialQuals()));

        this.remainingCol.setCellValueFactory(sc ->
                new SimpleStringProperty(
                        String.valueOf(sc.getValue().getRemainingCapacity())
                ));

        this.timeCol.setCellValueFactory(sc ->
                new SimpleStringProperty(
                        sc.getValue().getTimestamp().format(TIME_FORMAT)
                ));
    }

    /**
     * Populates the filter dropdowns.
     */
    private void populateFilters() {
        Authenticator auth = new Authenticator();
        this.crewFilter.setItems(FXCollections.observableArrayList(auth.getAllUsers()));
        this.specialFilter.setItems(FXCollections.observableArrayList(SpecialQualities.values()));
    }

    /**
     * Binds the filter UI to the ViewModel properties.
     */
    private void bindFilters() {
    	this.viewModel.crewFilterProperty().bindBidirectional(this.crewFilter.valueProperty());
    	this.viewModel.specialFilterProperty().bindBidirectional(this.specialFilter.valueProperty());
    	this.viewModel.startDateFilterProperty().bindBidirectional(this.startDateFilter.valueProperty());
    	this.viewModel.endDateFilterProperty().bindBidirectional(this.endDateFilter.valueProperty());
    }

    @FXML
    private void handleApplyFilters() {
        this.viewModel.applyFilters();
    }

    @FXML
    private void handleResetFilters() {
        this.viewModel.clearFilters();
    }

    @FXML
    private void handleHome() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LandingPage.fxml")
            );
            Parent root = loader.load();
            LandingPageCodeBehind controller = loader.getController();
            controller.init(this.currentUser);

            Stage stage = (Stage) this.changesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/ProjectOne/view/LoginPage.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) this.changesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
