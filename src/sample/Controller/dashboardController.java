package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Appointment;
import sample.Model.Customer;
import sample.Model.User;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.CustomerQuery;
import sample.Utilities.UserQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    public static String currentUserName;
    public static int currentUserID;

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
    public TextField customerSearch;
    public TextField appointmentSearch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerTable.setItems(CustomerQuery.getAllCustomers());
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
            customerCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            customerLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            customerLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

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

    public void onCustomerAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/addCustomerForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 415, 450);
        stage.setTitle("Add a Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onCustomerUpdate(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/updateCustomerForm.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            updateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Update a Customer");
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a Customer from the list");
            alert.showAndWait();
        }
    }

    public void onCustomerDelete(ActionEvent actionEvent) {
        try {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            for (Appointment appointment: appointmentTable.getItems()) {
                if (appointment.getCustomerID() == customer.getCustomerID()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Customer has associated Appointments, please delete these Appointments first");
                    alert.showAndWait();
                    break;
                }
            }

            CustomerQuery.delete(customer.getCustomerID());
            customerTable.setItems(CustomerQuery.getAllCustomers());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onAppointmentAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/addAppointmentForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 650, 400);
        stage.setTitle("Add an Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onAppointmentUpdate(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/updateAppointmentForm.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            updateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.sendAppointment(appointmentTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Update an Appointment");
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an appointment from the list");
            alert.showAndWait();
        }
    }

    public void onAppointmentDelete(ActionEvent actionEvent) {
        try {
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Appointment?");
            alert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                AppointmentQuery.delete(appointment.getAppointmentID());
                appointmentTable.setItems(AppointmentQuery.getAllAppointments());
            }
            else {
                alert.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onCustomerSearch(ActionEvent actionEvent) {
    }

    public void onAppointmentSearch(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Exit Dashboard");
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("../View/loginForm.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 450, 500);
            stage.setTitle("C195");
            stage.setScene(scene);
            stage.show();
        }
        else {
            alert.close();
        }

    }
}
