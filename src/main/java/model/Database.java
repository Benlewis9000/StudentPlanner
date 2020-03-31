package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class Database {


    // Singleton pattern reference
    private static final Database DATABASE = new Database();

    /**
     * Accessor to single Database instance.
     * @return the database instance.
     */
    public static Database getDatabase() {
        return DATABASE;
    }


    // Data being stored
    private HashMap<String, StudyProfile> studyProfiles;
    private HashMap<String, Module> modules;
    private HashMap<String, Deliverable> deliverables;
    private HashMap<String, StudyTask> studyTasks;
    private HashMap<String, Activity> activities;
    private HashMap<String, Note> notes;


    public Database(HashMap<String, StudyProfile> studyProfiles, HashMap<String, Module> modules, HashMap<String, Deliverable> deliverables,
                    HashMap<String, StudyTask> studyTasks, HashMap<String, Activity> activities, HashMap<String, Note> notes){

        this.studyProfiles = studyProfiles;
        this.modules = modules;
        this.deliverables = deliverables;
        this.studyTasks = studyTasks;
        this.activities = activities;
        this.notes = notes;

    }

    /** Todo: refactor to a load method?
     * Load the database into the singleton reference.
     */
    private Database(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {

            // Attempt to read in a data file
            JsonReader reader = new JsonReader(new FileReader(new File("database.json")));
            Database loadedDatabase = gson.fromJson(reader, Database.class);
            // Set study profiles to those loaded in
            studyProfiles = loadedDatabase.studyProfiles;
            System.out.println("Successfully read in \"database.json\".");

        }
        catch (IOException e){

            // ..else, create a new set of study profiles.
            System.out.println("Failed to read in data. Starting from scratch.");
            studyProfiles = new HashMap<String, StudyProfile>();
            modules = new HashMap<String, Module>();
            deliverables = new HashMap<String, Deliverable>();
            studyTasks = new HashMap<String, StudyTask>();
            activities = new HashMap<String, Activity>();
            notes = new HashMap<String, Note>();

        }

    }

    /**
     * Serialize and write out the current database state to "database.json".
     */
    public void saveDatabaseToFile(){

        // Create Gson object and serialize database to a String.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String databaseJson = gson.toJson(this);

        try {

            // Write json string out to file
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("database.json"), "utf-8"));
            writer.write(databaseJson);
            writer.close();


        }
        catch (IOException e){

            e.printStackTrace();

        }

    }

    public void addStudyProfile(StudyProfile studyProfile){

        studyProfiles.put(studyProfile.getID(), studyProfile);

    }

    public boolean containsStudyProfile(String uuid){

        return studyProfiles.containsKey(uuid);

    }

    public StudyProfile getStudyProfileFromUUID(String uuid) throws IllegalArgumentException{

        if (containsStudyProfile(uuid)){

            return studyProfiles.get(uuid);

        }
        else throw new IllegalArgumentException("StudyProfile " + uuid + " could not be found.");

    }

    public void deleteStudyProfile(String uuid){

        studyProfiles.remove(uuid);

    }


    public void addModule(Module module){

        modules.put(module.getID(), module);

    }

    public boolean containsModule(String uuid){

        return modules.containsKey(uuid);

    }

    public Module getModuleFromUUID(String uuid) throws IllegalArgumentException{

        if(containsModule(uuid)){

            return modules.get(uuid);

        }
        else throw new IllegalArgumentException("Module " + uuid + " could not be found.");

    }

    public void deleteModule(String uuid){

        modules.remove(uuid);

    }


    public void addDeliverable(Deliverable deliverable){

        deliverables.put(deliverable.getID(), deliverable);

    }

    public boolean containsDeliverable(String uuid){

        return deliverables.containsKey(uuid);

    }

    public Deliverable getDeliverableFromUUID(String uuid) throws IllegalArgumentException{

        if (containsDeliverable(uuid)){

            return deliverables.get(uuid);

        }
        else throw new IllegalArgumentException("Deliverable " + uuid + " could not be found.");

    }

    public void deleteDeliverable(String uuid){

        deliverables.remove(uuid);

    }


    public void addStudyTask(StudyTask studyTask){

        studyTasks.put(studyTask.getID(), studyTask);

    }

    public boolean containsStudyTask(String uuid){

        return studyTasks.containsKey(uuid);

    }

    public StudyTask getStudyTaskFromUUID(String uuid) throws IllegalArgumentException{

        if (containsStudyTask(uuid)){

            return studyTasks.get(uuid);

        }
        else throw new IllegalArgumentException("StudyTask " + uuid + " could not be found.");

    }

    public void deleteStudyTask(String uuid){

        studyTasks.remove(uuid);

    }


    public void addActivity(Activity activity){

        activities.put(activity.getID(), activity);

    }

    public boolean containsActivity(String uuid){

        return activities.containsKey(uuid);

    }

    public Activity getActivityFromUUID(String uuid) throws IllegalArgumentException{

        if (containsActivity(uuid)){

            return activities.get(uuid);

        }
        else throw new IllegalArgumentException("Activity " + uuid + " could not be found.");

    }

    public void deleteActivity(UUID uuid){

        activities.remove(uuid);

    }


    public void addNote(Note note){

        notes.put(note.getID(), note);

    }

    public boolean containsNote(String uuid){

        return notes.containsKey(uuid);

    }

    public Note getNoteFromUUID(String uuid) throws IllegalArgumentException{

        if (containsNote(uuid)){

            return notes.get(uuid);

        }
        else throw new IllegalArgumentException("Note " + uuid + " could not be found.");

    }

    public void deleteNote(String uuid){

        notes.remove(uuid);

    }

}
