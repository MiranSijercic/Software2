package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.User;
import sample.Utilities.UserQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class logInController implements Initializable {

    public Label regionLabel;
    public Label welcomeLabel;
    public Label usernameLabel;
    public Label passwordLabel;

    public TextField userNameField;
    public PasswordField passwordField;

    public Button loginButton;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        regionLabel.setText(Locale.getDefault().toString());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("sample/language", Locale.FRANCE);
            welcomeLabel.setText(rb.getString("welcome"));
            usernameLabel.setText(rb.getString("usernameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            userNameField.setPromptText(rb.getString("usernamePrompt"));
            passwordField.setPromptText(rb.getString("passwordPrompt"));
            loginButton.setText(rb.getString("login"));
            exitButton.setText(rb.getString("exit"));
        }

    }

    public void onLogin(ActionEvent actionEvent) throws IOException {
        try {
            String userName = userNameField.getText();
            String password = passwordField.getText();

            for (User user: UserQuery.getAllUsers()) {
                if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                    dashboardController.currentUserName = user.getUserName();
                    Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
                    Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 615, 700);
                    stage.setTitle("Dashboard");
                    stage.setScene(scene);
                    stage.show();
                    break;
                }

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Username or Password is not valid");
                alert.showAndWait();
                break;
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
