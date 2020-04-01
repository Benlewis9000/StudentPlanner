package model;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

public class Tests {

    public static void main(String[] args){

        testModel();

        //testDatabaseSave();

    }

    /**
     * First test to check whether GSON is set up and works.
     */
    public static void testGson(){

        Note note = new Note("Test Gson", "This is a test for Gson.", LocalDateTime.now());

        Gson gson = new Gson();
        String noteJson = gson.toJson(note);
        System.out.println(noteJson);

        try {

            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("note.json"), "utf-8"));

            writer.write(noteJson);

            writer.close();


        }
        catch (IOException e){

            e.printStackTrace();

        }

    }

    /**
     * Create a filled StudyProfile instance that is automatically saved to the database.
     * @return the StudyProfile created.
     */
    public static StudyProfile testModel(){

        //StudyProfile studyProfile = new StudyProfile(Semester.SPRING, Year.of(2020));
        StudyProfile studyProfile = new StudyProfile(Semester.AUTUMN, Year.of(2019));

        //Module softEng = new Module("SOFTWARE ENGINEERING 1", "Rudy Lapeer", "CMP-5012B");
        //Module networks = new Module("NETWORKS", "Ben Milner", "CMP-5037B");
        Module electronics = new Module("ELECTRONICS", "Ben Milner", "CMP-5037B");
        //studyProfile.addModule(softEng);
        //studyProfile.addModule(networks);
        studyProfile.addModule(electronics);

        Deliverable exam = new Deliverable(DeliverableType.EXAM, "Final Exam", "2 hours, room C. HALL.",
                LocalDate.of(2020, 6, 12), true);
        Deliverable milestone = new Deliverable(DeliverableType.MILESTONE, "Finish Revision", "Finish revising to start past papers",
                LocalDate.of(2020, 6, 6), false);
        electronics.addDeliverable(exam);
        electronics.addDeliverable(milestone);

        StudyTask studyTask1 = new StudyTask("Revision", 4,TaskType.REVISION);
        milestone.addStudyTask(studyTask1);

        Activity activity1 = new Activity("Revised gates", 3);
        studyTask1.addActivity(activity1);

        StudyTask studyTask2 = new StudyTask("Do Coursework", 5, TaskType.OTHER);
        Activity activity2 = new Activity("Familiarised self with equipment", 2);
        studyTask2.addActivity(activity2);

        milestone.addStudyTask(studyTask2);

        Database.exportAsSemesterProfile(studyProfile);

        return studyProfile;

    }

    /**
     * Test saving the database state out to a file.
     */
    public static void testDatabaseSave(){

        Database.getDatabase().saveDatabaseToFile();

    }

}
