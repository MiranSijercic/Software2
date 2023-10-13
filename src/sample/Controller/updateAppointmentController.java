package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.Model.Appointment;
import sample.Model.Contact;
import sample.Model.Customer;
import sample.Utilities.ContactQuery;
import sample.Utilities.CustomerQuery;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        for (int hour = 8; hour < 22; hour++) {
            times.add(LocalTime.of(hour, 0));
            times.add(LocalTime.of(hour, 15));
            times.add(LocalTime.of(hour, 30));
            times.add(LocalTime.of(hour, 45));
        }
        try {
            customerCombo.setItems(CustomerQuery.getAllCustomers());
            contactCombo.setItems(ContactQuery.getAllContacts());
            startCombo.setItems(times);
            endCombo.setItems(times);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void onSave(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {
    }
}
