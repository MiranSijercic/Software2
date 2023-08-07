package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.Utilities.UserQuery;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class logInController implements Initializable {
    public Label regionLabel;

    public TextField userNameField;
    public TextField passwordField;

    public Button loginButton;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onLogin(ActionEvent actionEvent) {
        try {
            String userName = userNameField.getText();
            String password = passwordField.getText();

            if (UserQuery.foundUser(userName, password)) {
                //todo add route to dashboard
            }

        }
        catch (NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Credentials can not be blank");
            alert.showAndWait();

        }

    }

    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Do you want to close the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
        else {
            alert.close();
        }
    }
}
