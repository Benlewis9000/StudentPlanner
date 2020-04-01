package controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Activity;
import model.Deliverable;
import model.StudyTask;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class StudyTasksController implements Initializable{


    @FXML
    private ListView<StudyTask> studyTasksListView;
    @FXML
    private Text studyTaskTitleText;
    @FXML
    private Text studyTaskHoursRequiredText;
    @FXML
    private Text studyTaskTypeText;
    @FXML
    private Text studyTaskCompletedText;
    @FXML
    private TextField activityDescriptionTextField;
    @FXML
    private TextField activityHoursTakenTextField;
    @FXML
    private Text feedbackText;

    private StudyTask lastSelected = null;


    // Deliverable to get studytasks/data from
    private Deliverable parentDeliverable;

    /**
     * Takes a deliverable to initialise the view.
     * @param deliverable to base data and actions on.
     */
    public void initData(Deliverable deliverable){

        parentDeliverable = deliverable;

        updateStudyTasksView();

    }


    /**
     * Display data for last selected study task.
     */
    public void studyTasksListViewClicked(){

        StudyTask studyTask = studyTasksListView.getSelectionModel().getSelectedItem();

        if (studyTask != null) {

            lastSelected = studyTask;

            studyTaskTitleText.setText(studyTask.getTitle());
            studyTaskHoursRequiredText.setText(String.valueOf(studyTask.getHoursRequired()));
            studyTaskTypeText.setText(studyTask.getType().toString());

            int hoursDone = 0;

            for (UUID uuid : studyTask.getActivityIDs()) {

                Activity activity = studyTask.getActivityFromUUID(uuid);

                hoursDone += activity.getHoursTaken();

            }

            String completed = (hoursDone >= studyTask.getHoursRequired()) ? "Yes" : "No";
            studyTaskCompletedText.setText(completed);

        }

    }

    /**
     * Add a new activity from input data.
     */
    public void addNewActivityButtonPressed(){

        // Get all selected StudyTasks
        ObservableList<StudyTask> studyTasks = studyTasksListView.getSelectionModel().getSelectedItems();

        // Get inputs
        String description = activityDescriptionTextField.getText();
        String hoursTakenStr = activityHoursTakenTextField.getText();

        // Null check
        if (description == ""
                || hoursTakenStr == ""){

            feedbackText.setText("ERROR: Fields may not be empty!");
            feedbackText.setVisible(true);

        }
        else {

            int hoursTaken;

            // Try to parse int given
            try {

                hoursTaken = Integer.parseInt(hoursTakenStr);

                // Create Activity instance
                Activity activity = new Activity(description, hoursTaken);

                // Add to each selected StudyTask
                for (StudyTask studyTask : studyTasks){

                    studyTask.addActivity(activity);

                }

                feedbackText.setText("Activity successfully added!");
                feedbackText.setVisible(true);

            }
            catch (NumberFormatException e){

                feedbackText.setText("ERROR: Fields may not be empty!");
                feedbackText.setVisible(true);

            }
        }

        updateStudyTasksView();
        studyTasksListViewClicked();

    }

    public void viewActivitiesButtonPressed(){

        // Get last selected
        StudyTask studyTask = lastSelected;

        // Null check
        if (lastSelected != null){

            try {

                changeToActivitiesView(studyTask);

            }
            catch (IOException e){

                // Display error messages
                feedbackText.setText("ERROR: Failed to load scene.");
                feedbackText.setVisible(true);
                System.out.println("Error: failed to load ActivitiesView.fxml. Could not create scene.");
                e.printStackTrace();

            }
        }
        // Else set and display error message.
        else {

            feedbackText.setText("ERROR: Please select a study task to view.");
            feedbackText.setVisible(true);

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void updateStudyTasksView(){

        // New Observable list
        ObservableList<StudyTask> studyTaskObservables = FXCollections.observableArrayList();

        // For each study task ID in the module..
        for (UUID uuid : parentDeliverable.getStudyTaskIDs()){

            // ..get study task instance and add to observables
            studyTaskObservables.add(parentDeliverable.getStudyTaskFromUUID(uuid));

        }

        // Sort alphabetically
        studyTaskObservables.sort((task1, task2) -> (task1.getTitle().compareToIgnoreCase(task2.getTitle())));
        // Set the study tasks list
        studyTasksListView.setItems(studyTaskObservables);
        studyTasksListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void changeToActivitiesView(StudyTask studyTask) throws IOException {

        // Load FXML and set as root
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ActivitiesView.fxml"));
        Parent activitiesRoot = loader.load();

        // Create scene
        Scene activitiesScene = new Scene(activitiesRoot);

        // Get ActivitiesView controller and initialise data
        ActivitiesController controller = loader.getController();
        controller.initData(studyTask);

        // Change scenes
        MainApplication.getApplication().getStage().setScene(activitiesScene);

    }

    public void goToOverviewScene(){

        MainApplication.getApplication().getStage().setScene(MainApplication.getApplication().getOverviewScene());

    }

}
