package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.JDBC;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/loginForm.fxml"));
        primaryStage.setTitle("C195");
        primaryStage.setScene(new Scene(root, 450, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // Locale.setDefault(Locale.FRANCE);
        TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago"));
        JDBC.makeConnection();
        launch(args);
    }
}

// First lambda is in onMonthCombo in sortedAppointmentsController
// Second lambda is in loginController onExit method

//todo: add state/city filtering to updateCustomer

