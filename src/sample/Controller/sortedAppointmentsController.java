package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Appointment;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.UtilityMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This Class is the controller for the Sorted Appointments screen.
 * Used to allow filtering of all Appointments by current month or current week.
 */
public class sortedAppointmentsController implements Initializable {
    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> appointmentIDCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> descriptionCol;
    public TableColumn<Appointment, String> locationCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, Timestamp> startCol;
    public TableColumn<Appointment, Timestamp> endCol;
    public TableColumn<Appointment, Timestamp> appointmentCreateDateCol;
    public TableColumn<Appointment, String> appointmentCreatedByCol;
    public TableColumn<Appointment, Timestamp> appointmentLastUpdateCol;
    public TableColumn<Appointment, String> appointmentLastUpdatedByCol;
    public TableColumn<Appointment, Integer> appointmentCustomerIDCol;
    public TableColumn<Appointment, Integer> appointmentUserIDCol;
    public TableColumn<Appointment, Integer> appointmentContactIDCol;

    public RadioButton monthRadio;
    public RadioButton weekRadio;
    public RadioButton showAllRadio;

    public ToggleGroup sortToggle;

    public Button returnButton;

    /**
     * Overrides initialize method to populate the Tableview with all Appointments.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
            appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            appointmentCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
            appointmentCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            appointmentLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            appointmentLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            appointmentContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses a lambda expression to sort all Appointments by current month and resets the Appointment table with the sorted observable list.
     * Lambda justification: used to avoid need to write an additional method for a sql query to sort appointment by current month
     * @param actionEvent handles selecting the 'Month' radio button
     * @throws SQLException to query database
     */
    public void onMonthRadio(ActionEvent actionEvent) throws IOException, SQLException {
        appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        ObservableList<Appointment> sortedAppointments = FXCollections.observableArrayList(
                appointmentTable.getItems().filtered(appointment -> appointment.getStart().toLocalDateTime().getMonth()
                        == LocalDateTime.now().getMonth()));
        appointmentTable.setItems(sortedAppointments);
    }

    /**
     * Uses appointmentByCurrentWeekSelect method from UtilityMethods Abstract class to sort Appointments by the current week.
     * Resets Appointment table with sorted observable list.
     * @param actionEvent handles selecting the 'Week' radio button
     * @throws SQLException to query database
     */
    public void onWeekRadio(ActionEvent actionEvent) throws SQLException {
        appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        appointmentTable.setItems(UtilityMethods.appointmentByCurrentWeekSelect());
    }

    /**
     * Resets the Appointment tableview to show all Appointments. Uses getAllAppointments method from AppointmentQuery Abstract class
     * @param actionEvent handles selecting the 'Show All' radio button
     * @throws SQLException to query database
     */
    public void onShowAllRadio(ActionEvent actionEvent) throws SQLException {
        appointmentTable.setItems(AppointmentQuery.getAllAppointments());
    }

    /**
     * Closes the Sorted Appointments screen
     * @param actionEvent handles clicking the 'Return' button
     * @throws IOException returns the user to the Dashboard screen
     */
    public void onReturnButton(ActionEvent actionEvent) throws IOException {
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

