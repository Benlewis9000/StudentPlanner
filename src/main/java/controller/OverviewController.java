package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import model.StudyProfile;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {

    @FXML
    private ListView<StudyProfile> studyProfileListView;
    @FXML
    private ScrollBar studyProfileScrollBar;
    @FXML
    private Button infoButton;
    @FXML
    private Button modulesButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button viewStudyDashboardButton;
    @FXML
    private Button newStudyProfileButton;


    public void infoButtonPressed(){

        System.out.println("Info button pressed.");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

}
