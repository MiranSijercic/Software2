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
import sample.Model.User;
import sample.Utilities.AppointmentQuery;
import sample.Utilities.UserQuery;
import sample.Utilities.UtilityQueries;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

public class userReportController implements Initializable {
    public ComboBox<User> userCombo;
    public ComboBox<Month> monthCombo;

    public TextField countField;

    public Button returnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userCombo.setItems(UserQuery.getAllUsers());
            monthCombo.getItems().addAll(Month.values());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUserCombo(ActionEvent actionEvent) throws SQLException {
        if (!monthCombo.getSelectionModel().isEmpty()) {
            countField.setText(String.valueOf(UtilityQueries.userAndMonthSelect(userCombo.getSelectionModel().getSelectedItem().getUserID(),
                    monthCombo.getSelectionModel().getSelectedIndex() + 1)));
        }
    }

    public void onMonthCombo(ActionEvent actionEvent) throws SQLException {
        if (!userCombo.getSelectionModel().isEmpty()) {
            countField.setText(String.valueOf(UtilityQueries.userAndMonthSelect(userCombo.getSelectionModel().getSelectedItem().getUserID(),
                    monthCombo.getSelectionModel().getSelectedIndex() + 1)));
        }
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
