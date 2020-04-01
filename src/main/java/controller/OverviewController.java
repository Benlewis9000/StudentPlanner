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


    public void modulesButtonPressed(){

        System.out.println("#modules button pressed.");
        MainApplication.getApplication().getStage().setScene(MainApplication.getApplication().getModulesScene());

    }

    public void deleteButtonPressed(){

        System.out.println("#delete button pressed.");

        // Get the StudyProfile selected
        StudyProfile studyProfile = studyProfileListView.getSelectionModel().getSelectedItem();

        // Delete from database
        Database.getDatabase().deleteStudyProfile(studyProfile.getID());

        // Update list view
        updateStudyProfileListView();

    }

    public void viewStudyDashboardButtonPressed(){

        // Todo: go to StudyDashbboard scene for selected profile
        System.out.println("#viewStudyDashboard button pressed.");

    }

    public void importStudyProfileButtonPressed(){

        System.out.println("#importStudyProfile button pressed.");

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

    /**
     * Update the data in the StudyProfile list view.
     */
    public void updateStudyProfileListView(){

        ObservableList<StudyProfile> studyProfileObservables = FXCollections.observableArrayList(Database.getDatabase().getStudyProfiles().values());
        // Sort by year
        studyProfileObservables.sort( (profile1, profile2) -> (profile1.getStartYear().compareTo(profile2.getStartYear())));
        studyProfileListView.setItems(studyProfileObservables);

    }

}
