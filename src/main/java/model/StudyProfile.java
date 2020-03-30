package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class StudyProfile {


    private final UUID ID;
    private final Semester semester;
    private final Year startYear;
    private HashSet<UUID> moduleIDs;
    private HashSet<UUID> activityIDs;

    /**
     * Constructor for a new StudyProfile, generating a random UUID.
     * @param semester semester of study.
     * @param startYear starting year (of year, not semester).
     */
    public StudyProfile(Semester semester, Year startYear){

        this.ID = UUID.randomUUID();
        this.semester = semester;
        this.startYear = startYear;
        this.moduleIDs = new HashSet<>();
        this.activityIDs = new HashSet<>();

        saveToDatabase();

    }

    /**
     * Get the UUID for the instance.
     * @return unique ID as UUID.
     */
    public UUID getID () { return ID; }

    public Year getStartYear() { return startYear; }


    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addStudyProfile(this);

    }


    // Test harness.
    public static void main(String[] args){

        testSerialization();

    }

    /**
     * Generate a dummy StudyProfile to use for testing.
     * @return a StudyProfile with some values.
     */
    public static StudyProfile generateDummy(){

        Module softEng = new Module("SOFTWARE ENGINEERING 1", "Rudy Lapeer", "CMP-5012B");
        Module networks = new Module("NETWORKS", "Ben Milner", "CMP-5037B");

        softEng.addDeliverable(Deliverable.generateDummy());

        HashMap<UUID, Module> modules = new HashMap<>();
        modules.put(softEng.getID(), softEng);
        modules.put(networks.getID(), networks);

        return new StudyProfile(Semester.SPRING, Year.of(2020)); // todo: update to new format (sets & database)
        // Todo: studyProfile.addModule(module) - need to implement add etc. first

    }

    /**
     * Test serialization and deserialization of class to Json.
     * @return true if serialized and deserialized Strings are equal.
     */
    public static void testSerialization() {

        // Generate dummy StudyProfile
        StudyProfile testProfile = StudyProfile.generateDummy();

        // Create Gson instance
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        // Serialize StudyProfile to Json
        String testProfileJson = gson.toJson(testProfile);

        // Attempt to load StudyProfile instance from Json
        StudyProfile testProfileObject = gson.fromJson(testProfileJson, StudyProfile.class);

        // Check worked by printing a value
        System.out.println(testProfileObject.getStartYear());

        // View whole Json String
        System.out.println(testProfileJson);

    }

}
