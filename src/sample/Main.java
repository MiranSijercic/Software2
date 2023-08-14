package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.JDBC;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/loginForm.fxml"));
        primaryStage.setTitle("C195");
        primaryStage.setScene(new Scene(root, 450, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}

// NullPointer trying to find dashboard.fxml

