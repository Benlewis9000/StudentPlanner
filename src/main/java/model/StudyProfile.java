package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class StudyProfile {

    @Id
    private UUID ID;
    private Semester semester;
    private Year startYear;
    @ElementCollection
    private Set<UUID> moduleIDs;

    /*
     * Empty constructor required for Hibernate
     */
    public StudyProfile(){}

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

        saveToDatabase();

    }


    /**
     * Get the UUID for the instance.
     * @return unique ID as UUID.
     */
    public UUID getID () { return ID; }

    public Semester getSemester() {
        return semester;
    }

    public Year getStartYear() {
        return startYear;
    }


    /**
     * Query whether the StudyProfile has the Module corresponding to the given UUID.
     * @param uuid UUID of the Module in question.
     * @return true if the UUID corresponds to a Module owned by this StudyProfile.
     */
    public boolean hasModule(UUID uuid){

        return moduleIDs.contains(uuid);

    }

    /**
     * Get the Module instance of a study task held by the StudyProfile.
     * @param uuid of the Module held.
     * @return the instance of the Module.
     * @throws IllegalArgumentException if the Module was not held, or could not be found in the database.
     */
    public Module getModuleFromUUID(UUID uuid) throws IllegalArgumentException{

        if (hasModule(uuid)){

            return Database.getDatabase().getModuleFromUUID(uuid);

        }
        else throw new IllegalArgumentException("Module " + uuid + " is not a member of StudyProfile + " + this.getID() + ".");

    }

    /**
     * Add a Module to the StudyProfile.
     * @param module to add.
     */
    public void addModule(Module module){

        moduleIDs.add(module.getID());

    }

    /**
     * Remove a Module from the StudyProfile (thus delete from database).
     * @param module to remove.
     */
    public void removeModule(Module module){

        moduleIDs.remove(module.getID());
        Database.getDatabase().deleteModule(module.getID());

    }

    /**
     * Get all IDs of modules owned by this StudyProfile.
     * @return set of UUIDs.
     */
    public Set<UUID> getModuleIDs() {
        return moduleIDs;
    }


    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addStudyProfile(this);

    }

    @Override
    public String toString(){

        int year1 = getStartYear().getValue();
        int year2 = year1 + 1;

        return year1 + "-" + year2 + " " + getSemester();

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
