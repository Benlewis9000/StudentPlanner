package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import model.Module;
import model.Semester;
import model.StudyProfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Year;
import java.util.ArrayList;

/**
 * Controller for the StudyProfile. Handles creation of new StudyProfiles by loading them from a Json File.
 */
public class StudyProfileController {


    private StudyProfile studyProfile;


    /**
     * Construct from a StudyProfile instance.
     * @param studyProfile controller relates to.
     */
    public StudyProfileController(StudyProfile studyProfile){

        this.studyProfile = studyProfile;

    }

    /**
     * Construct from a Json file of a serialized StudyProfile.
     * @param pathname path to file.
     * @throws FileNotFoundException if Json file could not be found.
     * @throws JsonSyntaxException if file could not be read.
     */
    public StudyProfileController(String pathname) throws FileNotFoundException, JsonSyntaxException{

        this.studyProfile = loadFromFile(pathname);

    }


    /**
     * Load a StudyProfile instance from a Json file.
     * @param pathname path to file.
     * @return StudyInstance created.
     * @throws FileNotFoundException if Json file could not be found.
     * @throws JsonSyntaxException if file could not be read.
     */
    public StudyProfile loadFromFile(String pathname) throws FileNotFoundException, JsonSyntaxException{

        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new FileReader(new File(pathname)));

        return gson.fromJson(reader, StudyProfile.class);

    }


    // Test harness.
    public static void main(String[] args){



    }



}
