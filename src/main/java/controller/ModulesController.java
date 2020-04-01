package controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
    private Text successText;
    @FXML
    private Text errorText;


    public void addNewMilestoneButtonPressed(){

        Module module = modulesListView.getSelectionModel().getSelectedItem();

        String title = milestoneTitleTextField.getText();
        String description = milestoneDescriptionTextField.getText();
        LocalDate deadline = milestoneDeadlineDatePicker.getValue();

        // Prematurely hide success text
        successText.setVisible(false);

        if (title.equals("")
                || description.equals("")
                || deadline == null
                || module == null){

            errorText.setText("ERROR: Fields may not be empty!");
            errorText.setVisible(true);

        }
        else if (deadline.isBefore(LocalDate.now())){

            errorText.setText("ERROR: The deadline may not be in the past!");
            errorText.setVisible(true);

        }
        else {

            Deliverable milestone = new Deliverable(DeliverableType.MILESTONE, title, description, deadline, false);
            Database.getDatabase().getModules().get(module.getID()).addDeliverable(milestone);
            errorText.setVisible(false);
            successText.setVisible(true);

        }

    }

    public void viewDeliverablesButtonPressed(){

        // todo

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        successText.setVisible(false);
        errorText.setVisible(false);

        updateModulesListView();

        // Todo: does OverviewController need to update here? Probably not since it updates when opening,
        //          and can only be opened after finishing an import.

    }

    public void updateModulesListView(){

        // TODO: Only load modules that belong to the parent STUDY PROFILE!

        ObservableList<Module> moduleObservables = FXCollections.observableArrayList(Database.getDatabase().getModules().values());
        // Sort by module code alphabetically
        moduleObservables.sort( (module1, module2) -> (module1.getModuleCode().compareTo(module2.getModuleCode())));
        modulesListView.setItems(moduleObservables);

    }

}
