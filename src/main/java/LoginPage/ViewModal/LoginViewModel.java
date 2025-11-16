package LoginPage.ViewModal;

import edu.westga.cs3211.User.model.Authenticator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel for the login screen. 
 * @author ns00184
 * @version Fall 2025
 */
public class LoginViewModel {

    private final StringProperty username;
    private final StringProperty password;

    private final Authenticator authenticator;

    /**
     * Creates a new LoginViewModel.
     * 
     * @precondition none
     * @postcondition username and password are empty strings
     */
    public LoginViewModel() {
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.authenticator = new Authenticator();
    }

    /**
     * Returns the username property.
     * 
     * @return the username StringProperty
     */
    public StringProperty usernameProperty() {
        return this.username;
    }

    /**
     * Returns the password property.
     * 
     * @return the password StringProperty
     */
    public StringProperty passwordProperty() {
        return this.password;
    }

    /**
     * Determines whether both username and password fields contain
     * non-empty values.
     * 
     * @return true if both fields are filled
     */
    public boolean canLogin() {
        return !this.username.get().trim().isEmpty()
            && !this.password.get().trim().isEmpty();
    }

    /**
     * Attempts to log the user in by verifying the username and password
     * with the Authenticator model.
     * 
     * @return true if login succeeds, false otherwise
     * 
     * @precondition canLogin() == true
     * @postcondition none
     */
    public boolean login() {
        return this.authenticator.verify(
            this.username.get(),
            this.password.get()
        );
    }
}


