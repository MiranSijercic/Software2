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

/**
 * This Class is the controller for the Login screen.
 * User is directed to this screen on Application launch.
 */
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

    /**
     * Overrides initialize method checks System timezone and language, then populates languageLabel and zoneLabel with corresponding information.
     * Translates the page and alerts according to the System language.
     */
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

    /**
     * Verifies User's username and password. Logs login attempt. If user is verified, checks if there is an appointment within the next 15 minutes and provides Alert.
     * @param actionEvent handles clicking the 'Login' button
     * @throws IOException opens dashboard.fxml, with corresponding application window
     */
    public void onLogin(ActionEvent actionEvent) throws IOException {
        try {
            String userName = userNameField.getText();
            String password = passwordField.getText();

            for (User user : UserQuery.getAllUsers()) {
                if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                    LocalTime loginTime = LocalTime.now();
                    for (Appointment appointment : AppointmentQuery.getAllAppointments()) {
                        LocalTime appointmentStart = appointment.getStart().toLocalDateTime().toLocalTime();
                        long timeDifference = java.time.Duration.between(loginTime, appointmentStart).toMinutes();;
                        if (timeDifference <= 15 && timeDifference >= 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Upcoming Appointment");
                            alert.setContentText("Upcoming appointment with ID: " + appointment.getAppointmentID() + " is on " + appointment.getStart().toLocalDateTime().toLocalDate()
                                    + " at " + appointment.getStart().toLocalDateTime().toLocalTime());
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

    /**
     * Closes the application. Handles the Confirmation Alert with a lambda expression.
     * Lambda justification: used to bypass additional if/else statement tied to user clicking 'Ok' button on confirmation Alert.
     * @param actionEvent handles clicking the 'Exit' button
     * @throws IOException Closes the application
     */
    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(exit);
        alert.setHeaderText(exitHeader);
        alert.setContentText(exitMessage);
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> System.exit(0));
    }
}
