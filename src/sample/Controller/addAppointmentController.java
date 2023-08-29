package sample.Controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.Model.Contact;
import sample.Model.Customer;
import sample.Utilities.ContactQuery;
import sample.Utilities.CustomerQuery;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;

    public ComboBox<Customer> customerCombo;
    public ComboBox<Contact> contactBox;
    public ComboBox<Timestamp> startBox;
    public ComboBox<Timestamp> endBox;

    public DatePicker datePicker;

    public Button save;
    public Button exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerCombo.setItems(CustomerQuery.getAllCustomers());
            contactBox.setItems(ContactQuery.getAllContacts());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
