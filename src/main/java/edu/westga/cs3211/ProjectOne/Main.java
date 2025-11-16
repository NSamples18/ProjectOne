package edu.westga.cs3211.ProjectOne;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

/**
 * Main Application class.
 * Loads LoginPage.fxml first.
 * 
 * @author ns00184
 * @version Fall 2025
 */
public class Main extends Application {

    private static final String LOGIN_FXML = "/edu/westga/cs3211/ProjectOne/view/LoginPage.fxml";

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(LOGIN_FXML));
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    /** 
     *  Entry point for the application 
     *  @param args not used */
    public static void main(String[] args) {
        launch(args);
    }
}
