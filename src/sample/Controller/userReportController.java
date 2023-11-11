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
import sample.Model.Appointment;
import sample.Utilities.AppointmentQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class userReportController implements Initializable {
    ObservableList<Integer> userIDList = FXCollections.observableArrayList();
    ObservableList<Integer> appointmentCount = FXCollections.observableArrayList();

    public TableView<Integer> table;
    public TableColumn<Integer, Integer> userIDColumn;
    public TableColumn<Integer, Integer> appointmentColumn;
    public Button returnButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            for (Appointment appointment: AppointmentQuery.getAllAppointments()) {
                userIDList.add(appointment.getUserID());
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onReturnButton(ActionEvent actionEvent) throws IOException {
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
