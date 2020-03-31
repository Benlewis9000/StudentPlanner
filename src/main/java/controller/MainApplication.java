package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) throws IOException{

       //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/OverviewView.fxml"));
       Parent root = FXMLLoader.load(this.getClass().getResource("/OverviewView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Study Planner");
        stage.setResizable(false);
        stage.show();

    }
}
