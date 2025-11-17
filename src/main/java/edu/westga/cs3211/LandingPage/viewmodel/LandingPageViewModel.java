package edu.westga.cs3211.LandingPage.viewmodel;

import edu.westga.cs3211.User.model.UserRole;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LandingPageViewModel {

    private StringProperty greeting;
    private BooleanProperty viewStockVisible;

    public LandingPageViewModel() {
        this.greeting = new SimpleStringProperty("");
        this.viewStockVisible = new SimpleBooleanProperty(false);
    }

    public StringProperty greetingProperty() {
        return this.greeting;
    }

    public BooleanProperty viewStockVisibleProperty() {
        return this.viewStockVisible;
    }

    public void initialize(String username, UserRole role) {
        this.greeting.set("Hello, " + username + "!");

        this.viewStockVisible.set(role == UserRole.Quartermaster);
    }
}
