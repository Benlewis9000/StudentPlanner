package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Deliverable;
import model.Module;
import model.StudyTask;
import model.TaskType;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

public class DeliverablesController implements Initializable {


    @FXML
    private ListView<Deliverable> deliverablesListView;
    @FXML
    private Text deliverableTitleText;
    @FXML
    private Text deliverableDescriptionText;
    @FXML
    private Text deliverableDeadlineText;
    @FXML
    private Text deliverableTypeText;
    @FXML
    private Text deliverableSummativeText;
    @FXML
    private TextField studyTaskTitleTextField;
    @FXML
    private TextField studyTaskHoursRequiredTextField;
    @FXML
    private ChoiceBox<TaskType> studyTaskTypeChoiceBox;
    @FXML
    private Button addNewStudyTaskButton;
    @FXML
    private Text feedbackText;


    // Module to get deliverables/data from
    private Module parentModule;

    /**
     * Takes a module to initialise the view.
     * @param module to base data and actions on.
     */
    public void initData(Module module){

        parentModule = module;

        updateDeliverablesListView();

    }

    /**
     * Update the deliverable info displayed when a deliverable is clicked in the observable list.
     */
    public void deliverablesListViewClicked(){

        // Get deliverable selected
        Deliverable deliverable = deliverablesListView.getSelectionModel().getSelectedItem();

        // Null check
        if (deliverable != null){

            // Set data
            deliverableTitleText.setText(deliverable.getTitle());
            deliverableDescriptionText.setText(deliverable.getDescription());
            deliverableDeadlineText.setText(deliverable.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            deliverableTypeText.setText(deliverable.getType().toString());
            String summativeText = (deliverable.isSummative()) ? "Yes" : "No";
            deliverableSummativeText.setText(summativeText);

        }

    }



    public void viewStudyTasksButtonPressed(){

        System.out.println("#viewStudyTasksButtonPressed");

        // todo: go to studytasks view and pass a studytask to initData()

    }

    /**
     * Create a new Study Task and add to Deliverable selected.
     */
    public void addNewStudyTaskButtonPressed(){

        Deliverable deliverable = deliverablesListView.getSelectionModel().getSelectedItem();
        String title = studyTaskTitleTextField.getText();
        String hours = studyTaskHoursRequiredTextField.getText();
        TaskType type = studyTaskTypeChoiceBox.getSelectionModel().getSelectedItem();

        // Make sure fields are not empty/null
        if (deliverable == null
                || title == ""
                || hours == ""
                || type == null){

            feedbackText.setText("ERROR: Fields may not be empty!");
            feedbackText.setVisible(true);
            return;

        }

        int hoursInt;

        try {

            hoursInt = Integer.parseInt(hours);

            // Prevent hours less than 0
            if (hoursInt < 1){

                feedbackText.setText("ERROR: Hours required may not be less than 1!");
                feedbackText.setVisible(true);

            }
            else {

                // Create study task instance and add to deliverable
                StudyTask studyTask = new StudyTask(title, hoursInt, type);
                deliverable.addStudyTask(studyTask);
                // Display success text
                feedbackText.setText("Successfully added Study Task!");
                feedbackText.setVisible(true);

            }

        }
        catch (NumberFormatException e){

            feedbackText.setText("ERROR: Fields may not be empty!");
            feedbackText.setVisible(true);

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set options in choice box as all TaskType values
        ObservableList<TaskType> taskTypes = FXCollections.observableArrayList(Arrays.asList(TaskType.values()));
        studyTaskTypeChoiceBox.getItems().addAll(taskTypes);

    }


    /**
     * Reload and update the deliverables displayed in the observable list.
     */
    public void updateDeliverablesListView(){

        // New Observable list
        ObservableList<Deliverable> deliverableObservables = FXCollections.observableArrayList();

        // For each deliverable ID in the module..
        for (UUID uuid : parentModule.getDeliverableIDs()){

            // ..get deliverable instance and add to observables
            deliverableObservables.add(parentModule.getDeliverableFromUUID(uuid));

        }

        // Sort by deadline
        deliverableObservables.sort((deliverable1, deliverable2) -> (deliverable1.getDeadline().compareTo(deliverable2.getDeadline())));
        // Set the deliverables list
        deliverablesListView.setItems(deliverableObservables);

    }

}
