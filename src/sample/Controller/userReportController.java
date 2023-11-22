package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.User;
import sample.Utilities.UserQuery;
import sample.Utilities.UtilityMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This Class is the controller for the Appointments by User screen, called from the Dashboard Screen.
 * This was my additional report of choice. This is used as a metric to measure the number of appointments Users are scheduling each month.
 */
public class userReportController implements Initializable {
    public ComboBox<User> userCombo;
    public ComboBox<Month> monthCombo;

    public TextField countField;

    public Button returnButton;

    /**
     * Overrides initialize method to populate the user and month combo boxes with corresponding data
     * @throws SQLException to query database
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userCombo.setItems(UserQuery.getAllUsers());
            monthCombo.getItems().addAll(Month.values());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks that month combo has a selection, then uses userAndMonthSelect method from UtilityMethods Abstract class to get the count of Appointments.
     * Passes the user and month selections to userAndMonthSelect method
     * @param actionEvent handles selecting a User
     * @throws SQLException to query database
     */
    public void onUserCombo(ActionEvent actionEvent) throws SQLException {
        if (!monthCombo.getSelectionModel().isEmpty()) {
            countField.setText(String.valueOf(UtilityMethods.userAndMonthSelect(userCombo.getSelectionModel().getSelectedItem().getUserID(),
                    monthCombo.getSelectionModel().getSelectedIndex() + 1)));
        }
    }

    /**
     * Checks that user combo has a selection, then uses userAndMonthSelect method from UtilityMethods Abstract class to get the count of Appointments.
     * Passes the user and month selections to userAndMonthSelect method
     * @param actionEvent handles selecting a Month
     * @throws SQLException to query database
     */
    public void onMonthCombo(ActionEvent actionEvent) throws SQLException {
        if (!userCombo.getSelectionModel().isEmpty()) {
            countField.setText(String.valueOf(UtilityMethods.userAndMonthSelect(userCombo.getSelectionModel().getSelectedItem().getUserID(),
                    monthCombo.getSelectionModel().getSelectedIndex() + 1)));
        }
    }

    /**
     * Closes the Appointments by User screen
     * @param actionEvent handles clicking the 'Return' button
     * @throws IOException returns the user to the Dashboard screen
     */
    public void onReturn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return");
        alert.setHeaderText("Return to Dashboard");
        alert.setContentText("Do you want to return to the dashboard?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        } else {
            alert.close();
        }
    }

}
