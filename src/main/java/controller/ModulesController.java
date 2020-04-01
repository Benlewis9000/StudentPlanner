package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

public class ModulesController implements Initializable {


    @FXML
    private ListView<Module> modulesListView;
    @FXML
    private TextField milestoneTitleTextField;
    @FXML
    private TextField milestoneDescriptionTextField;
    @FXML
    private DatePicker milestoneDeadlineDatePicker;
    @FXML
    private Button addNewMilestoneButton;
    @FXML
    private Text feedbackText;
    @FXML
    private Text moduleCodeText;
    @FXML
    private Text moduleTitleText;
    @FXML
    private Text moduleOrganiserText;


    // StudyProfile to get modules/data from
    private StudyProfile parentStudyProfile;
    private Module selectedModule = null;


    /**
     * Takes a study profile to initialise the view.
     * @param studyProfile to base data and actions on.
     */
    public void initData(StudyProfile studyProfile){

        parentStudyProfile = studyProfile;

        updateModulesListView();

    }


    /**
     * Update the module info displayed when a module is clicked in the observable list.
     */
    public void modulesListViewClicked(){

        // Get module clicked
        Module module = modulesListView.getSelectionModel().getSelectedItem();

        // Check module is not null
        if (module != null){

            selectedModule = module;

            // Set module info in scene
            moduleCodeText.setText(module.getModuleCode());
            moduleTitleText.setText(module.getModuleTitle());
            moduleOrganiserText.setText(module.getModuleOrganiser());

        }

    }

    /**
     * Go to the deliverables view for the module selected.
     */
    public void viewDeliverablesButtonPressed(){

        System.out.println("#viewDeliverablesButtonPressed");

        Module module = modulesListView.getSelectionModel().getSelectedItem();

        if (module != null){

            try {

                // Pass module and switch scene
                changeToDeliverablesView(module);

                // Hide feedback
                feedbackText.setVisible(false);

            }
            catch (IOException e){

                // Display error messages
                feedbackText.setText("ERROR: Failed to load scene.");
                feedbackText.setVisible(true);
                System.out.println("Error: failed to load DeliverablesView.fxml. Could not create scene.");
                e.printStackTrace();

            }

        }
        // Else set and display error message.
        else {

            feedbackText.setText("ERROR: Please select a deliverable to view.");
            feedbackText.setVisible(true);

        }

    }

    /**
     * Create a new milestone Deliverable from data entered and add it the Module selected.
     */
    public void addNewMilestoneButtonPressed(){

        // Get data selected
        Module module = modulesListView.getSelectionModel().getSelectedItem();
        String title = milestoneTitleTextField.getText();
        String description = milestoneDescriptionTextField.getText();
        LocalDate deadline = milestoneDeadlineDatePicker.getValue();

        // Prent null/empty fields
        if (title.equals("")
                || description.equals("")
                || deadline == null
                || module == null){

            feedbackText.setText("ERROR: Fields may not be empty!");
            feedbackText.setVisible(true);

        }
        // Prevent date already passed
        else if (deadline.isBefore(LocalDate.now())){

            feedbackText.setText("ERROR: The deadline may not be in the past!");
            feedbackText.setVisible(true);

        }
        // Add milestone
        else {

            // Create milestone and add to parent Module
            Deliverable milestone = new Deliverable(DeliverableType.MILESTONE, title, description, deadline, false);
            module.addDeliverable(milestone);
            // Display success text
            feedbackText.setText("Successfully added Milestone!");
            feedbackText.setVisible(true);

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        feedbackText.setVisible(false);

    }


    /**
     * Change to the DeliverablesView scene for a select StudyProfile.
     * @param module to view deliverables of.
     * @throws IOException if fails to load fxml resource.
     */
    public void changeToDeliverablesView(Module module) throws IOException {

        // Load FXML file and set as root
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/DeliverablesView.fxml"));
        Parent deliverablesRoot = loader.load();

        // Create scene
        Scene deliverablesScene = new Scene(deliverablesRoot);

        // Get DeliverablesView controller and initialise study profile data
        DeliverablesController controller = loader.getController();
        controller.initData(module);

        // Change scenes
        MainApplication.getApplication().getStage().setScene(deliverablesScene);

    }

    /**
     * Reload and update the modules displayed in the observable list.
     */
    public void updateModulesListView(){

        // New Observable list
        ObservableList<Module> moduleObservables = FXCollections.observableArrayList();

        // For each module ID in the study profile..
        for (UUID uuid : parentStudyProfile.getModuleIDs()){

            // ..get the Module instance and add to observables
            moduleObservables.add(parentStudyProfile.getModuleFromUUID(uuid));

        }

        // Sort by module code alphabetically
        moduleObservables.sort( (module1, module2) -> (module1.getModuleCode().compareTo(module2.getModuleCode())));
        // Set the module list
        modulesListView.setItems(moduleObservables);

    }

}
