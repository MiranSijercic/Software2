package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Model.Country;
import sample.Model.FLD;

import java.net.URL;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {

    public TextField customerIDField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    
    public ComboBox<FLD> stateCombo;
    public ComboBox<Country> countryCombo;
    public TextField phoneField;
    
    public Button save;
    public Button exit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void onSave(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {
    }
}