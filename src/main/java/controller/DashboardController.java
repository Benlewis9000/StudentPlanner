package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Database;
import model.Deliverable;
import model.StudyTask;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class DashboardController implements Initializable {

    @FXML
    VBox rightBox;
    @FXML
    ScrollPane completedScrollPane;
    @FXML
    ScrollPane upcomingScrollPane;
    @FXML
    ScrollPane missedScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initData();

    }

    /**
     * Initialise the dynamic data to be displayed in the scene.
     */
    public void initData(){

        Map<Deliverable, Double> cDeliv = new HashMap<>();
        Map<Deliverable, Double> uDeliv = new HashMap<>();
        Map<Deliverable, Double> fDeliv = new HashMap<>();

        // Populate maps
        for (Deliverable d : Database.getDatabase().getDeliverables().values()){

            int hoursRequired = 0;
            int hoursDone = 0;

            for (UUID sTid : d.getStudyTaskIDs()){

                StudyTask sT = Database.getDatabase().getStudyTaskFromUUID(sTid);
                hoursRequired += sT.getHoursRequired();

                for (UUID aId : sT.getActivityIDs()){

                    hoursDone += Database.getDatabase().getActivityFromUUID(aId).getHoursTaken();

                }

            }

            // Get progress percentage - default to 1 (complete) if none needed.
            double progress;
            if (hoursRequired == 0) progress = 1;
            else progress = (double)hoursDone/(double)hoursRequired;

            // Sort deliverables into correct map
            if (hoursDone < hoursRequired && d.getDeadline().isBefore(LocalDate.now())) fDeliv.put(d, Double.valueOf(progress));
            else if (hoursDone < hoursRequired) uDeliv.put(d, Double.valueOf(progress));
            else cDeliv.put(d, Double.valueOf(progress));

        }

        // Populate scroll panes
        populateScrollPane((VBox) completedScrollPane.getContent(), cDeliv, Color.GREEN);
        populateScrollPane((VBox) upcomingScrollPane.getContent(), uDeliv, Color.BLACK);
        populateScrollPane((VBox) missedScrollPane.getContent(), fDeliv, Color.RED);

        System.out.println("Finished initData");

    }

    private void populateScrollPane(VBox vbox, Map<Deliverable, Double> deliverables, Color color){

        vbox.setAlignment(Pos.TOP_LEFT);

        // Iterate deliverables passed
        for (Deliverable d : deliverables.keySet()){

            // Get data
            Text title = new Text(d.getTitle());
            Text deadline  = new Text(d.getDeadline().toString());
            ProgressBar pB = new ProgressBar(deliverables.get(d));
            // Parent node deliverable data
            VBox innerVbox = new VBox();
            innerVbox.getChildren().add(title);
            innerVbox.getChildren().add(deadline);
            innerVbox.getChildren().add(pB);
            // Assign colors and formatting
            title.setFill(color);
            deadline.setFill(Color.GRAY);
            StackPane sp = new StackPane();
            sp.setPadding(new Insets(0, 0, 5, 0));
            // Assign node children
            sp.getChildren().add(innerVbox);
            vbox.getChildren().add(sp);

        }

    }

    public void goToOverviewScene(){

        System.out.println("Dashboard#overview button pressed");
        MainApplication.getApplication().getStage().setScene(MainApplication.getApplication().getOverviewScene());

    }

}
