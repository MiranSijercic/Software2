package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Appointment;
import sample.Model.User;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.UserQuery;
import sample.Utilities.UtilityMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class logInController implements Initializable {

    public Label zoneLabel;
    public Label languageLabel;
    public Label welcomeLabel;
    public Label usernameLabel;
    public Label passwordLabel;

    public TextField userNameField;
    public PasswordField passwordField;

    public Button loginButton;
    public Button exitButton;

    public String errorMessage;
    public String error;
    public String exit;
    public String exitHeader;
    public String exitMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        languageLabel.setText(String.valueOf(Locale.getDefault()));
        zoneLabel.setText(String.valueOf(ZoneId.systemDefault()));

        ResourceBundle rb = ResourceBundle.getBundle("sample/language", Locale.ENGLISH);
        welcomeLabel.setText(rb.getString("welcome"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        userNameField.setPromptText(rb.getString("usernamePrompt"));
        passwordField.setPromptText(rb.getString("passwordPrompt"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        errorMessage = rb.getString("errorMessage");
        exitHeader = rb.getString("exitHeader");
        exitMessage = rb.getString("exitMessage");

        if (Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb2 = ResourceBundle.getBundle("sample/language", Locale.FRANCE);
            welcomeLabel.setText(rb2.getString("welcome"));
            usernameLabel.setText(rb2.getString("usernameLabel"));
            passwordLabel.setText(rb2.getString("passwordLabel"));
            userNameField.setPromptText(rb2.getString("usernamePrompt"));
            passwordField.setPromptText(rb2.getString("passwordPrompt"));
            loginButton.setText(rb2.getString("login"));
            exitButton.setText(rb2.getString("exit"));
            errorMessage = rb2.getString("errorMessage");
            exitHeader = rb2.getString("exitHeader");
            exitMessage = rb2.getString("exitMessage");
        }
    }

    public void onLogin(ActionEvent actionEvent) throws IOException {
        try {
            String userName = userNameField.getText();
            String password = passwordField.getText();

            for (User user : UserQuery.getAllUsers()) {
                if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                    LocalTime loginTime = LocalTime.now();
                    for (Appointment appointment : AppointmentQuery.getAllAppointments()) {
                        LocalTime appointmentStart = appointment.getStart().toLocalDateTime().toLocalTime();
                        long timeDifference = ChronoUnit.MINUTES.between(appointmentStart, loginTime);
                        if ((timeDifference * -1) > 0 && (timeDifference * -1) < 30) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Upcoming Appointment");
                            alert.setContentText("Upcoming appointment with ID: " + appointment.getAppointmentID() + " on " + appointment.getStart().toLocalDateTime().toLocalDate()
                                    + " at " + appointment.getStart().toLocalDateTime().toLocalTime());
                            alert.showAndWait();
                            break;
                        }
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setContentText("There are no upcoming appointments");
                    alert.showAndWait();

                    UtilityMethods.logToFile("Login attempt: Successful. Time: " + LocalDateTime.now() + ". User ID: "
                            + user.getUserID());
                    dashboardController.currentUserName = user.getUserName();
                    dashboardController.currentUserID = user.getUserID();
                    Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 900, 700);
                    stage.setTitle("Dashboard");
                    stage.setScene(scene);
                    stage.show();
                    return;

                }
            }

            UtilityMethods.logToFile("Login attempt: Unsuccessful. Time: " + LocalDateTime.now());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(error);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(exit);
        alert.setHeaderText(exitHeader);
        alert.setContentText(exitMessage);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
        else {
            alert.close();
        }
    }
}
