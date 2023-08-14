package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.Appointment;
import sample.Model.Customer;
import sample.Utilities.AppointmentQuery;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIDCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> postalCodeCol;
    public TableColumn<Customer, String> phoneCol;
    public TableColumn<Customer, Timestamp> customerCreateDateCol;
    public TableColumn<Customer, String> customerCreatedByCol;
    public TableColumn<Customer, Timestamp> customerLastUpdateCol;
    public TableColumn<Customer, String> customerLastUpdatedByCol;
    public TableColumn<Customer, Integer> divisionIDCol;
    
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

    public Button customerAdd;
    public Button customerUpdate;
    public Button customerDelete;
    public Button appointmentAdd;
    public Button appointmentUpdate;
    public Button appointmentDelete;
    public Button exit;


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
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
    }

    public void onCustomerAdd(ActionEvent actionEvent) {
    }

    public void onCustomerUpdate(ActionEvent actionEvent) {
    }

    public void onCustomerDelete(ActionEvent actionEvent) {
    }

    public void onAppointmentAdd(ActionEvent actionEvent) {
    }

    public void onAppointmentUpdate(ActionEvent actionEvent) {
    }

    public void onAppointmentDelete(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {
    }
}
