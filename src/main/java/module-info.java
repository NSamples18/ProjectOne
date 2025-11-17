module edu.westga.cs3211.ProjectOne {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens edu.westga.cs3211.LoginPage.View to javafx.fxml;
    opens edu.westga.cs3211.LandingPage.view to javafx.fxml;
    opens edu.westga.cs3211.ProjectOne to javafx.fxml;
    opens edu.westga.cs3211.AddStock.view to javafx.fxml;

    exports edu.westga.cs3211.LoginPage.View;
    exports edu.westga.cs3211.LoginPage.ViewModel;
    exports edu.westga.cs3211.LandingPage.view;
    exports edu.westga.cs3211.ProjectOne;
    exports edu.westga.cs3211.User.model;
}
