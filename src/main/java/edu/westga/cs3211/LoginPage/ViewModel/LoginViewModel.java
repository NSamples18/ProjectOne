package edu.westga.cs3211.LoginPage.ViewModel;

import edu.westga.cs3211.User.model.Authenticator;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel for login handling.
 * @author ns00184
 * @version 2025
 */
public class LoginViewModel {

    private final Authenticator authenticator;

    private final StringProperty username;
    private final StringProperty password;
    private final ObjectProperty<User> loggedInUser;

    public LoginViewModel() {
        this.authenticator = new Authenticator();
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.loggedInUser = new SimpleObjectProperty<>(null);
    }

    public StringProperty usernameProperty() {
        return this.username;
    }

    public StringProperty passwordProperty() {
        return this.password;
    }

    public User getLoggedInUser() {
        return this.loggedInUser.get();
    }

    /**
     * Performs login and stores the logged-in user.
     *
     * @return true if successful
     */
    public boolean login() {
        String name = this.username.get();
        String pass = this.password.get();

        User user = this.authenticator.getUserIfValid(name, pass);
        if (user == null) {
            return false;
        }

        this.loggedInUser.set(user);
        return true;
    }
}
