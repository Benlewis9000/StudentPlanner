package controller;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Database;
import model.StudyProfile;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {

    @FXML
    private ListView<StudyProfile> studyProfileListView;
    @FXML
    private ScrollBar studyProfileScrollBar;
    @FXML
    private Button modulesButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button viewStudyDashboardButton;
    @FXML
    private TextField semesterProfilePathTextField;
    @FXML
    private Button importStudyProfileButton;
    @FXML
    private Text failedToImportText;


    public void infoButtonPressed(){

        System.out.println("Info button pressed.");

    }

    public void modulesButtonPressed(){

        // Todo: go to new Scene that displays modules
        System.out.println("Modules button pressed.");

    }

    public void deleteButtonPressed(){

        // Todo: delete the StudyProfile from database and remove from list
        System.out.println("Delete button pressed.");

    }

    public void viewStudyDashboardButtonPressed(){

        // Todo: go to StudyDashbboard scene for selected profile
        System.out.println("viewStudyDashboard button pressed.");

    }

    public void importStudyProfileButtonPressed(){

        // Todo: go to new Scene to take in String of file location and load file then go to it's dashboard(?)
        System.out.println("importStudyProfile button pressed.");

        // Get path from text field input
        String path = semesterProfilePathTextField.getText();

        System.out.println(path);

        // Attempt to import and show error message if failure.
        if(Database.importSemesterProfile(path)) {

            failedToImportText.setVisible(false);
            semesterProfilePathTextField.setText("");

        }
        else failedToImportText.setVisible(false);

        // Update the data displayed.
        updateStudyProfileListView();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateStudyProfileListView();

        failedToImportText.setVisible(false);

    }

    public void updateStudyProfileListView(){

        ObservableList<StudyProfile> studyProfileObservables = FXCollections.observableArrayList(Database.getDatabase().getStudyProfiles().values());
        // Sort by year
        studyProfileObservables.sort( (profile1, profile2) -> (profile1.getStartYear().compareTo(profile2.getStartYear())));
        studyProfileListView.setItems(studyProfileObservables);

    }

}
