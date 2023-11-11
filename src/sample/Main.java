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

// 1 lambda is in onMonthCombo in sortedAppointmentsController


// 15 minute alert triggers for all appointments
// FILTERING: month as combobox? how does week need to work?
// setting a tableview with observable lists instead of objects? see userReport
// do we need to be able to sort by type WITH month?
// is it enough to have contact schedule be a tableview as well?


