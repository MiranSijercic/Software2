package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Contact;
import sample.Model.Customer;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.ContactQuery;
import sample.Utilities.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

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

    public void onSave(ActionEvent actionEvent) throws SQLException {
        try {
            if (!(customerCombo.getSelectionModel().isEmpty() || titleField.getText().isEmpty() || descriptionField.getText().isEmpty()
                    || locationField.getText().isEmpty() || typeField.getText().isEmpty() || contactCombo.getSelectionModel().isEmpty()
                    || datePicker.getValue() == null || startCombo.getSelectionModel().isEmpty() || endCombo.getSelectionModel().isEmpty())) {
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

                if (startTimestamp.before(endTimestamp)) {
                    AppointmentQuery.insert(title, description, location, type, startTimestamp, endTimestamp, createDate, createdBy,
                            lastUpdate, lastUpdatedBy, customerID, userID, contactID);
                    Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 615, 700);
                    stage.setTitle("Dashboard");
                    stage.setScene(scene);
                    stage.show();
                }

                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("End time must be later than start time!");
                    alert.showAndWait();
                }
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

    public void onExit(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return");
        alert.setHeaderText("Return to Dashboard");
        alert.setContentText("Do you want to return to the dashboard?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 615, 700);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        }
        else {
            alert.close();
        }
    }
}
