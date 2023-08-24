package sample.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Country;
import sample.Model.FLD;
import sample.Utilities.CountryQuery;
import sample.Utilities.FLDQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

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
            countryCombo.setItems(CountryQuery.getAllCountries());
        }

        catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void onSave(ActionEvent actionEvent) {
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
