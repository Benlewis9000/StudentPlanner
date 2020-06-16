package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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

    public void initData(){

        List<Deliverable> cDeliv = new ArrayList<>();
        List<Deliverable> uDeliv = new ArrayList<>();
        List<Deliverable> fDeliv = new ArrayList<>();

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

            if (hoursDone < hoursRequired && d.getDeadline().isBefore(LocalDate.now())) fDeliv.add(d);
            else if (hoursDone > hoursRequired) cDeliv.add(d);
            else uDeliv.add(d);

        }

        populatePane((VBox) completedScrollPane.getContent(), cDeliv, Color.GREEN);
        populatePane((VBox) upcomingScrollPane.getContent(), uDeliv, Color.BLACK);
        populatePane((VBox) missedScrollPane.getContent(), fDeliv, Color.RED);

        System.out.println("Finished initData");

        /*
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 100; i++){
            System.out.println(i);
            Text t = new Text("Text " + i);
            t.setTextAlignment(TextAlignment.CENTER);
            StackPane sp = new StackPane();
            sp.setAlignment(Pos.CENTER);
            sp.setPrefHeight(10);
            sp.getChildren().add(t);
            //rightBox.getChildren().add(sp);
            scrollPaneAnchor.getChildren().add(sp);
            vbox.getChildren().add(sp);

            scrollPane.setContent(vbox);
            scrollPane.setPannable(true);


        }
         */

    }

    private void populatePane(VBox vbox, List<Deliverable> deliverables, Color color){

        for (Deliverable d : deliverables){

            Text title = new Text(d.getTitle());
            Text deadline  = new Text(d.getDeadline().toString());
            VBox innerVbox = new VBox();
            innerVbox.getChildren().add(title);
            innerVbox.getChildren().add(deadline);
            title.setFill(color);
            deadline.setFill(Color.GRAY);
            StackPane sp = new StackPane();
            sp.setPadding(new Insets(5, 0, 5, 0));
            sp.getChildren().add(innerVbox);
            vbox.getChildren().add(sp);

        }

    }

    public void goToOverviewScene(){

        System.out.println("Dashboard#overview button pressed");
        MainApplication.getApplication().getStage().setScene(MainApplication.getApplication().getOverviewScene());

    }

}
