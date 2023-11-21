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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This Class is the controller for the Dashboard screen.
 * User is directed here after successful login. This serves as the hub to access all other parts of the application.
 */
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

    public Button sortedAppointments;
    public Button appointmentsByType;
    public Button appointmentsByUser;
    public Button contactSchedules;

    public Button exit;

    /**
     * Overrides initialize method to populate the Tableviews of Appointments and Customers, respectively.
     * Tableviews are populated by querying the database using the getAllCustomers and getAllAppointments methods.
     * @throws SQLException to query database
     */
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

    /**
     * Sends the user to the Add Customer Screen
     * @param actionEvent handles clicking the 'Add' button below the 'Customers' table
     * @throws IOException opens addCustomerForm.fxml, with corresponding application window
     */
    public void onCustomerAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/addCustomerForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 415, 450);
        stage.setTitle("Add a Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Collects selected Customer data and Sends the user to the Update Customer Screen
     * @param actionEvent handles clicking the 'Update' button below the 'Customers' table
     * @throws IOException opens updateCustomerForm.fxml, with corresponding application window
     */
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

    /**
     * Used to delete Customers from database. Will prompt the User to first delete any existing Appointments associated with the Customer.
     * @param actionEvent handles clicking the 'Delete' button below the 'Customers' table.
     * @throws SQLException calls delete method from CustomerQuery Abstract Class to remove Customer from the database
     */
    public void onCustomerDelete(ActionEvent actionEvent) {
        try {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            for (Appointment appointment: appointmentTable.getItems()) {
                if (appointment.getCustomerID() == customer.getCustomerID()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(customer.getCustomerName() + " has associated Appointments, please delete these Appointments first");
                    alert.showAndWait();
                    return;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setContentText("Customer data for " + customer.getCustomerName() + " has been deleted");
            alert.showAndWait();

            CustomerQuery.delete(customer.getCustomerID());
            customerTable.setItems(CustomerQuery.getAllCustomers());
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the user to the Add Appointment Screen
     * @param actionEvent handles clicking the 'Add' button below the 'Appointments' table
     * @throws IOException opens addAppointmentForm.fxml, with corresponding application window
     */
    public void onAppointmentAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/addAppointmentForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 650, 400);
        stage.setTitle("Add an Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Collects selected Appointment data and Sends the user to the Update Appointment Screen
     * @param actionEvent handles clicking the 'Update' button below the 'Appointments' table
     * @throws IOException opens updateAppointmentForm.fxml, with corresponding application window
     */
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

    /**
     * Used to delete Appointments from database. Will prompt user for confirmation.
     * @param actionEvent handles clicking the 'Delete' button below the 'Appointments' table.
     * @throws SQLException calls delete method from AppointmentQuery Abstract Class to remove Appointment from the database
     */
    public void onAppointmentDelete(ActionEvent actionEvent) {
        try {
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Appointment?");
            alert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Update");
                alert2.setContentText("Appoint with ID: " + appointment.getAppointmentID() + " and of Type: " +
                        appointment.getType() + " has been canceled.");
                alert2.showAndWait();

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

    /**
     * Sends the user to the Appointment Sort Screen
     * @param actionEvent handles clicking the 'Sorted Appointments' button
     * @throws IOException opens sortedAppointmentsForm.fxml, with corresponding application window
     */
    public void onSortedAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/sortedAppointmentsForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 650, 450);
        stage.setTitle("Appointment Sort");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the user to the Appointments by Type Screen
     * @param actionEvent handles clicking the 'Appointments By Type' button
     * @throws IOException opens typeReportForm.fxml, with corresponding application window
     */
    public void onAppointmentsByType(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/typeReportForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 420, 430);
        stage.setTitle("Appointments by Type");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the user to the Appointment Count By User Screen
     * @param actionEvent handles clicking the 'Appointments By User' button
     * @throws IOException opens userReportForm.fxml, with corresponding application window
     */
    public void onAppointmentsByUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/userReportForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 420, 430);
        stage.setTitle("Appointment Count by User");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the user to the Contact Schedule Screen
     * @param actionEvent handles clicking the 'Contact Schedules' button
     * @throws IOException opens contactScheduleForm.fxml, with corresponding application window
     */
    public void onContactSchedules(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/contactScheduleForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 440);
        stage.setTitle("Contact Schedule");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logs the user out of the Application
     * @param actionEvent handles clicking the 'Exit' button
     * @throws IOException returns the user to the Login Screen
     */
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
