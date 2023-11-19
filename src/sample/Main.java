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

        String fileName = "login_activity.txt", record;
    }
}

// 1 lambda is in onMonthCombo in sortedAppointmentsController


//todo: Finish 15 minute alert
//todo: Confirm that weekly sorts from sortedAppointments and contactSchedule are calendar vs static +7 days from login?
//todo: find another lambda opportunity

