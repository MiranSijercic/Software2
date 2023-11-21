package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Appointment;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.UtilityMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This Class is the controller for the Appointments By Type screen, called from the Dashboard Screen.
 * Used to find the total count of Appointments by selected type.
 */
public class typeReportController implements Initializable {
    public ComboBox<Month> monthCombo;
    public ComboBox<String> typeCombo;

    public TextField countField;

    public Button returnButton;

    /**
     * Overrides initialize method to populate month and type combo boxes, respectively.
     * @throws SQLException to query database
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            monthCombo.getItems().addAll(Month.values());
            for (Appointment appointment: AppointmentQuery.getAllAppointments()) {
                if (!typeCombo.getItems().contains(appointment.getType())) {
                    typeCombo.getItems().add(appointment.getType());
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks that type combo has a selection, the uses monthAndTypeSelect method from UtilityMethods Abstract class to get the count of Appointments.
     * Passes the type and month selections to monthAndTypeSelect method
     * @param actionEvent handles selecting a month
     * @throws SQLException to query database
     */
    public void onMonthCombo(ActionEvent actionEvent) throws SQLException {
        if (!typeCombo.getSelectionModel().isEmpty()) {
            countField.setText(String.valueOf(UtilityMethods.monthAndTypeSelect(typeCombo.getSelectionModel().getSelectedItem(),
                    monthCombo.getSelectionModel().getSelectedIndex() + 1)));
        }
    }

    /**
     * Checks that month combo has a selection, the uses monthAndTypeSelect method from UtilityMethods Abstract class to get the count of Appointments.
     * Passes the type and month selections to monthAndTypeSelect method
     * @param actionEvent handles selecting a type
     * @throws SQLException to query database
     */
    public void onTypeCombo(ActionEvent actionEvent) throws SQLException {
        if (!monthCombo.getSelectionModel().isEmpty()) {
            countField.setText(String.valueOf(UtilityMethods.monthAndTypeSelect(typeCombo.getSelectionModel().getSelectedItem(),
                    monthCombo.getSelectionModel().getSelectedIndex() + 1)));
        }
    }

    /**
     * Closes the Appointments by Type screen
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
