package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Appointment;
import sample.Model.Contact;
import sample.Utilities.ContactQuery;
import sample.Utilities.UtilityMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class contactScheduleController implements Initializable {
    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> appointmentIDCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, String> descriptionCol;
    public TableColumn<Appointment, Timestamp> startCol;
    public TableColumn<Appointment, Timestamp> endCol;
    public TableColumn<Appointment, Integer> customerIDCol;

    public ComboBox<Contact> contactCombo;

    public Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            contactCombo.setItems(ContactQuery.getAllContacts());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onContactCombo(ActionEvent actionEvent) throws SQLException {
        appointmentTable.setItems(UtilityMethods.customerScheduleSelect(contactCombo.getSelectionModel().getSelectedItem().getContactID()));
    }

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
