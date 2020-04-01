package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Database;
import model.StudyProfile;

import java.io.IOException;
import java.net.URL;
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
    private Text feedbackText;


    /**
     * Go to the modules view for the study profile selected.
     */
    public void modulesButtonPressed(){

        System.out.println("#modules button pressed.");

        // Get the StudyProfile selected
        StudyProfile studyProfile = studyProfileListView.getSelectionModel().getSelectedItem();

        // Check a study profile was selected
        if (studyProfile != null) {

            try {

                // Pass study profile and switch scene
                changeToModulesView(studyProfile);

                // Hide feedback message
                feedbackText.setVisible(false);

            } catch (IOException e) {

                // Display error messages
                feedbackText.setText("ERROR: Failed to load scene.");
                feedbackText.setVisible(true);
                System.out.println("Error: failed to load ModulesView.fxml. Could not create scene.");
                e.printStackTrace();

            }


        }
        // Else set and display error message.
        else {

            feedbackText.setText("ERROR: Please select a study profile to view.");
            feedbackText.setVisible(true);

        }

    }

    /**
     *
     */
    public void deleteButtonPressed(){

        System.out.println("#delete button pressed.");

        // Get the StudyProfile selected
        StudyProfile studyProfile = studyProfileListView.getSelectionModel().getSelectedItem();

        if (studyProfile != null) {

            // Delete from database
            Database.getDatabase().deleteStudyProfile(studyProfile.getID());

            // Set and display success message.
            feedbackText.setText("Successfully deleted study profile. ");
            feedbackText.setVisible(true);

            // Update list view
            updateStudyProfileListView();

        }
        else {

            // Set and display error message.
            feedbackText.setText("ERROR: Please select a study profile to delete.");
            feedbackText.setVisible(true);

        }

    }

    /**
     * Attempt to import a semester profile from the path given in a text field.
     */
    public void importStudyProfileButtonPressed(){

        System.out.println("#importStudyProfile button pressed.");

        // Get path from text field input
        String path = semesterProfilePathTextField.getText();

        System.out.println(path);

        // Attempt to import and show error message if failure.
        if(!Database.importSemesterProfile(path)) {

            // Set and display error message.
            feedbackText.setText("ERROR: Failed to import semester profile.");
            feedbackText.setVisible(true);

        }
        else {

            // Set and display success message, clear fields
            feedbackText.setVisible(true);
            feedbackText.setText("Successfully imported semester profile! ");
            semesterProfilePathTextField.setText("");

        }

        // Update the data displayed.
        updateStudyProfileListView();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateStudyProfileListView();

        feedbackText.setVisible(false);

    }

    /**
     * Change to the ModulesView scene for a select StudyProfile.
     * @param studyProfile to view modules of.
     * @throws IOException if fails to load fxml resource.
     */
    public void changeToModulesView(StudyProfile studyProfile) throws IOException{

        // Load FXML file and set as root
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ModulesView.fxml"));
        Parent modulesRoot = loader.load();

        // Create scene
        Scene modulesScene = new Scene(modulesRoot);

        // Get ModulesView controller and initialise study profile data
        ModulesController controller = loader.getController();
        controller.initData(studyProfile);

        // Change scenes
        MainApplication.getApplication().getStage().setScene(modulesScene);

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
