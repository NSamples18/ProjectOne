package LoginPage.ViewModal;

import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;
import edu.westga.cs3211.User.model.Authenticator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel for the Login page.
 * MVVM-correct version: does NOT expose User objects.
 */
public class LoginViewModel {

    private StringProperty username;
    private StringProperty password;

    private StringProperty loggedInUsername;   // what we expose to other screens
    private ObjectProperty<UserRole> loggedInRole;

    private Authenticator authenticator;

    /**
     * Creates the LoginViewModel.
     */
    public LoginViewModel() {
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");

        this.loggedInUsername = new SimpleStringProperty("");
        this.loggedInRole = new SimpleObjectProperty<>(null);

        this.authenticator = new Authenticator();
    }

    public StringProperty usernameProperty() {
        return this.username;
    }

    public StringProperty passwordProperty() {
        return this.password;
    }

    public StringProperty loggedInUsernameProperty() {
        return this.loggedInUsername;
    }

    public ObjectProperty<UserRole> loggedInRoleProperty() {
        return this.loggedInRole;
    }

    /**
     * Attempts login using the authenticator.
     * @return 
     */
    public boolean login() {
        User user = this.authenticator.getUserIfValid(
                this.username.get(),
                this.password.get()
        );

        if (user != null) {
            this.loggedInUsername.set(user.getName());
            this.loggedInRole.set(user.getRole());
            return true;
        }

        return false;
    }
}
