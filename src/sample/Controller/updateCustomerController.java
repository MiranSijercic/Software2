package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Country;
import sample.Model.Customer;
import sample.Model.FLD;
import sample.Utilities.CountryQuery;
import sample.Utilities.CustomerQuery;
import sample.Utilities.FLDQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {

    private Timestamp createDate;
    private String createdBy;

    public TextField customerIDField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    
    public ComboBox<FLD> stateCombo;
    public ComboBox<Country> countryCombo;
    
    public Button save;
    public Button exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            stateCombo.setItems(FLDQuery.getAllFLD());
            stateCombo.setVisibleRowCount(5);
            countryCombo.setItems(CountryQuery.getAllCountries());
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendCustomer(Customer selectedCustomer) {
        customerIDField.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameField.setText(selectedCustomer.getCustomerName());
        addressField.setText(selectedCustomer.getAddress());
        postalCodeField.setText(selectedCustomer.getPostalCode());
        phoneField.setText(selectedCustomer.getPhone());
        for (FLD fld: stateCombo.getItems()) {
            if (fld.getDivisionID() == selectedCustomer.getDivisionID()) {
                stateCombo.setValue(fld);
                for (Country country: countryCombo.getItems()) {
                    if (fld.getCountryID() == country.getCountryID()) {
                        countryCombo.setValue(country);
                        break;
                    }
                }
                break;
            }
        }
        this.createDate = selectedCustomer.getCreateDate();
        this.createdBy = selectedCustomer.getCreatedBy();
    }

    public void onSave(ActionEvent actionEvent) {
        try {
            if (!(customerIDField.getText().isEmpty() || nameField.getText().isEmpty() || addressField.getText().isEmpty()
            || postalCodeField.getText().isEmpty() || phoneField.getText().isEmpty()
            || countryCombo.getSelectionModel().isEmpty() || stateCombo.getSelectionModel().isEmpty())) {
                int id = Integer.parseInt(customerIDField.getText());
                String name = nameField.getText();
                String address = addressField.getText();
                String postalCode = postalCodeField.getText();
                String phone = phoneField.getText();
                Timestamp createDate = this.createDate;
                String createdBy = this.createdBy;
                Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
                String lastUpdatedBy = dashboardController.currentUserName;
                int divisionID = stateCombo.getSelectionModel().getSelectedItem().getDivisionID();

                CustomerQuery.update(id, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy,
                        divisionID);

                Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 615, 700);
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
        catch (NullPointerException | SQLException | IOException e) {
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
