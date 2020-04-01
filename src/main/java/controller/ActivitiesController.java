package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.Activity;
import model.StudyTask;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class ActivitiesController implements Initializable {

    @FXML
    private ListView<Activity> activitiesListView;
    @FXML
    private Text activityDescriptionText;
    @FXML
    private Text activityHoursTakenText;


    private StudyTask parentStudyTask;

    /**
     * Takes a studyTask to initialise the view.
     * @param studyTask to base data and actions on.
     */
    public void initData(StudyTask studyTask){

        parentStudyTask = studyTask;

        updateStudyTasksView();

    }

    /**
     * Update the activity info displayed when a activity is clicked in the observable list.
     */
    public void activitiesListViewClicked(){

        // Get activity selected
        Activity activity = activitiesListView.getSelectionModel().getSelectedItem();

        // Null check
        if (activity != null){

            // Set data
            activityDescriptionText.setText(activity.getDescription());
            activityHoursTakenText.setText(String.valueOf(activity.getHoursTaken()));

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void updateStudyTasksView(){

        // New Observable list
        ObservableList<Activity> activityObservables = FXCollections.observableArrayList();

        // For each study task ID in the module..
        for (UUID uuid : parentStudyTask.getActivityIDs()){

            // ..get study task instance and add to observables
            activityObservables.add(parentStudyTask.getActivityFromUUID(uuid));

        }

        // Sort alphabetically
        activityObservables.sort((activity1, activity2) -> (activity1.getDescription().compareToIgnoreCase(activity2.getDescription())));
        // Set the study tasks list
        activitiesListView.setItems(activityObservables);

    }

    public void goToOverviewScene(){

        MainApplication.getApplication().getStage().setScene(MainApplication.getApplication().getOverviewScene());

    }

}
