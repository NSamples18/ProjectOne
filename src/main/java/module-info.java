module edu.westga.cs3211.ProjectOne {
    requires javafx.controls;
    requires javafx.fxml;

    opens LoginPage.View to javafx.fxml;
    opens edu.westga.cs3211.LandingPage.view to javafx.fxml;
    opens edu.westga.cs3211.ProjectOne to javafx.fxml;

    exports LoginPage.View;
    exports LoginPage.ViewModal;
    exports edu.westga.cs3211.LandingPage.view;
    exports edu.westga.cs3211.ProjectOne;
    exports edu.westga.cs3211.User.model;
}
