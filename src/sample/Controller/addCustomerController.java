package sample.Controller;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Country;
import sample.Model.FLD;
import sample.Model.User;
import sample.Utilities.CountryQuery;
import sample.Utilities.CustomerQuery;
import sample.Utilities.FLDQuery;
import sample.Utilities.UserQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * This Class is the controller for the Add Customer screen, called from the Dashboard Screen.
 * Used by Users to add Customers to the database.
 */
public class addCustomerController implements Initializable {

    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;

    public ComboBox<FLD> stateCombo;
    public ComboBox<Country> countryCombo;

    public Button save;
    public Button exit;

    /**
     * Overrides initialize method to populate the State and Country combo boxes with corresponding data.
     */
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

    /**
     * Filters available States/Territories in the stateCombo combo box to only those belonging to the selected Country.
     * @param actionEvent handles selecting a country from the countryCombo combo box
     * @throws SQLException to query database
     */
    public void onCountrySelect(ActionEvent actionEvent) throws SQLException {
        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
        ObservableList<FLD> filteredFLD = FXCollections.observableArrayList();
        int countryID = selectedCountry.getCountryID();
        switch (countryID) {
            case 1:
                for (FLD fld: FLDQuery.getAllFLD()) {
                    if (fld.getCountryID() == 1) {
                        filteredFLD.add(fld);
                    }

                }
                break;
            case 2:
                for (FLD fld: FLDQuery.getAllFLD()) {
                    if (fld.getCountryID() == 2) {
                        filteredFLD.add(fld);
                    }
                }
                break;
            case 3:
                for (FLD fld: FLDQuery.getAllFLD()) {
                    if (fld.getCountryID() == 3) {
                        filteredFLD.add(fld);
                    }
                }
                break;
        }
        stateCombo.setItems(filteredFLD);
    }

    /**
     * Stores data from all fields and creates a new Customer object, checks that no field is empty and catches Exceptions
     * @param actionEvent handles clicking the 'Save' button
     * @throws IOException returns the User to the Dashboard screen
     * @throws SQLException to query database
     */
    public void onSave(ActionEvent actionEvent) {
        try {
            if (!(nameField.getText().isEmpty() || addressField.getText().isEmpty() || postalCodeField.getText().isEmpty()
            || postalCodeField.getText().isEmpty() || phoneField.getText().isEmpty()
                    || countryCombo.getSelectionModel().isEmpty() || stateCombo.getSelectionModel().isEmpty())) {
                String name = nameField.getText();
                String address = addressField.getText();
                String postalCode = postalCodeField.getText();
                String phone = phoneField.getText();
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                String createdBy = dashboardController.currentUserName;
                Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
                String lastUpdatedBy = dashboardController.currentUserName;
                int divisionID = stateCombo.getSelectionModel().getSelectedItem().getDivisionID();

                CustomerQuery.insert(name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy,
                        divisionID);

                Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
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

        catch (NullPointerException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the Add Customer screen
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
