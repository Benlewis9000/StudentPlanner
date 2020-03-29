package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;

public class StudyProfile {


    private final Semester semester;
    private Year startYear;
    private HashSet<Module> modules;  // todo: hashset?


    public StudyProfile(Semester semester, Year startYear, HashSet<Module> modules){

        this.semester = semester;
        this.startYear = startYear;
        this.modules = modules;

    }

    public Year getStartYear() { return startYear; }

    public HashSet<Module> getModules() { return modules; }


    public void setModules(HashSet<Module> modules) {

        this.modules = modules;

    }

    // Todo - controller or model? - PROBABLY MODEL
    public void addModule(Module m){

        modules.add(m);

    }

    public void removeModule(Module m){

        modules.remove(m);

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

        HashSet<Module> modules = new HashSet<>();
        modules.add(softEng);
        modules.add(networks);

        return new StudyProfile(Semester.SPRING, Year.of(2020), modules);

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
