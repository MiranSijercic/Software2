package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Appointment;
import sample.Model.Contact;
import sample.Model.Customer;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.ContactQuery;
import sample.Utilities.CustomerQuery;
import sample.Utilities.UtilityMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This Class is the controller for the Update Appointment screen, called from the Dashboard Screen.
 * Used by Users to change or reschedule Appointments.
 */
public class updateAppointmentController implements Initializable {

    public TextField appointmentIDField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    
    public ComboBox<Customer> customerCombo;
    public ComboBox<Contact> contactCombo;
    public ComboBox<LocalTime> startCombo;
    public ComboBox<LocalTime> endCombo;

    public DatePicker datePicker;
    
    public Button save;
    public Button exit;

    /**
     * Overrides initialize method to populate customer, contact, start, and end combo boxes with corresponding data.
     * Uses convertedTimes method from UtilityMethods Abstract Class to convert star and end times to be within business hours
     * @throws SQLException to query database
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerCombo.setItems(CustomerQuery.getAllCustomers());
            contactCombo.setItems(ContactQuery.getAllContacts());
            startCombo.setItems(UtilityMethods.convertedTimes());
            endCombo.setItems(UtilityMethods.convertedTimes());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to pass Appointment data to, and populate the fields of, the Update Appointment Form
     * @param appointment is the Appointment selected from the Appointment Table in the Dashboard screen
     */
    public void sendAppointment(Appointment appointment) {
        for (Customer customer: customerCombo.getItems()) {
            if (customer.getCustomerID() == appointment.getCustomerID()) {
                customerCombo.setValue(customer);
            }
        }
        appointmentIDField.setText(String.valueOf(appointment.getAppointmentID()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());
        for (Contact contact: contactCombo.getItems()) {
            if (contact.getContactID() == appointment.getContactID()) {
                contactCombo.setValue(contact);
            }
        }
        datePicker.setValue(appointment.getStart().toLocalDateTime().toLocalDate());
        startCombo.setValue(appointment.getStart().toLocalDateTime().toLocalTime());
        endCombo.setValue(appointment.getEnd().toLocalDateTime().toLocalTime());
    }

    /**
     * Stores data from all fields and creates and updates the existing Appointment object, checks for Appointment time overlap/clashes and catches Exceptions
     * @param actionEvent handles clicking the 'Save' button
     * @throws SQLException to query database
     */
    public void onSave(ActionEvent actionEvent) throws SQLException {
        try {
            if (!(customerCombo.getSelectionModel().isEmpty() || titleField.getText().isEmpty() || descriptionField.getText().isEmpty()
                    || locationField.getText().isEmpty() || typeField.getText().isEmpty() || contactCombo.getSelectionModel().isEmpty()
                    || datePicker.getValue() == null || startCombo.getSelectionModel().isEmpty() || endCombo.getSelectionModel().isEmpty())) {
                int appointmentID = Integer.parseInt(appointmentIDField.getText());
                String title = titleField.getText();
                String description = descriptionField.getText();
                String location = locationField.getText();
                String type = typeField.getText();
                LocalDateTime startDateTime = LocalDateTime.of(datePicker.getValue(), startCombo.getValue());
                Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
                LocalDateTime endDateTime = LocalDateTime.of(datePicker.getValue(), endCombo.getValue());
                Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                String createdBy = dashboardController.currentUserName;
                Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
                String lastUpdatedBy = dashboardController.currentUserName;
                int customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomerID();
                int userID = dashboardController.currentUserID;
                int contactID = contactCombo.getSelectionModel().getSelectedItem().getContactID();

                // check that selected start time is before selected end time
                if (!(startTimestamp.before(endTimestamp))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("End time must be later than start time!");
                    alert.showAndWait();
                }
                // checking for appointment overlap with the provided start time
                for (Appointment appointment: AppointmentQuery.getAllAppointments()) {
                    if (appointment.getCustomerID() == customerID && appointment.getAppointmentID() != appointmentID &&
                            (appointment.getStart().toLocalDateTime().isBefore(startDateTime) ||
                                    appointment.getStart().toLocalDateTime().isEqual(startDateTime)) &&
                            startDateTime.isBefore(appointment.getEnd().toLocalDateTime())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Customer has an existing appointment during the requested start time");
                        alert.showAndWait();
                        return;
                    }
                }
                // checking for appointment overlap with the provided end time
                for (Appointment appointment: AppointmentQuery.getAllAppointments()) {
                    if (appointment.getCustomerID() == customerID && appointment.getAppointmentID() != appointmentID &&
                            (endDateTime.isAfter(appointment.getStart().toLocalDateTime()) &&
                                    (endDateTime.isBefore(appointment.getEnd().toLocalDateTime()) ||
                                            endDateTime.isEqual(appointment.getEnd().toLocalDateTime())))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Customer has an existing appointment starting before the requested end time");
                        alert.showAndWait();
                        return;
                    }
                }
                // checking for appointment overlap with both provided start and end times (appointment does not encompass existing appointment)
                for (Appointment appointment: AppointmentQuery.getAllAppointments()) {
                    if (appointment.getCustomerID() == customerID && appointment.getAppointmentID() != appointmentID &&
                            (startDateTime.isBefore(appointment.getStart().toLocalDateTime()) &&
                                    endDateTime.isAfter(appointment.getEnd().toLocalDateTime()))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Customer has an existing appointment within the requested window");
                        alert.showAndWait();
                        return;
                    }
                }

                AppointmentQuery.update(appointmentID, title, description, location, type, startTimestamp, endTimestamp,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
                Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 900, 700);
                stage.setTitle("Dashboard");
                stage.setScene(scene);
                stage.show();
            }

            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("All fields are required.");
                alert.showAndWait();
            }
        }
        catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the Update Appointment screen
     * @param actionEvent handles clicking the 'Exit' button
     * @throws IOException returns the user to the Dashboard screen
     */
    public void onExit(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return");
        alert.setHeaderText("Return to Dashboard");
        alert.setContentText("Do you want to return to the dashboard?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        }
        else {
            alert.close();
        }
    }
}
