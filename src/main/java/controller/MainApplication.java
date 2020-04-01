package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import model.Semester;

import java.io.IOException;

public class MainApplication extends Application {

    // Singleton reference to application
    private static MainApplication instance;

    private Stage stage;
    private Scene overviewScene;

    /**
     * Get the single instance for the application.
     * @return application singleton.
     */
    public static MainApplication getApplication(){
        return instance;
    }

    public MainApplication(){

        super();

        // Prevent second instance of MainApplication if one already exists
        if (instance != null) throw new UnsupportedOperationException("Singleton MainApplication constructor called more than once. Blocking.");
        else instance = this;

    }

    /**
     * Get Stage being used.
     * @return stage in use.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Get scene instance for the OverviewView.
     * @return the scene.
     */
    public Scene getOverviewScene() {
        return overviewScene;
    }

    @Override
    public void start(Stage stage) throws IOException{

        this.stage = stage;

        Parent overviewRoot = FXMLLoader.load(this.getClass().getResource("/OverviewView.fxml"));
        overviewScene = new Scene(overviewRoot);

        // Set up scene
        stage.setScene(overviewScene);
        stage.setTitle("Study Planner");
        stage.setResizable(false);
        stage.show();

    }

    @Override
    public void stop(){

        Database.getDatabase().saveDatabaseToFile();

    }

    public static void main(String[] args) {

        launch(args);

    }

}
