package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.JDBC;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/loginForm.fxml"));
        primaryStage.setTitle("C195");
        primaryStage.setScene(new Scene(root, 450, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        ResourceBundle rb = ResourceBundle.getBundle("sample/language", Locale.FRANCE);
        System.out.println(rb.getString("hello"));
        JDBC.makeConnection();
        launch(args);
    }
}

// NullPointer trying to find dashboard.fxml. Why not with loginform?
// how property files need to work
// most efficient way to verify user. Tracking full user object vs just userID

// add dropdown for user in appointment add