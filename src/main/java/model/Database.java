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
    private HashMap<UUID, StudyProfile> studyProfiles;
    private HashMap<UUID, Module> modules;
    private HashMap<UUID, Deliverable> deliverables;
    private HashMap<UUID, StudyTask> studyTasks;
    private HashMap<UUID, Activity> activities;
    private HashMap<UUID, Note> notes;


    /**
     * Load the database into the singleton reference.
     */
    private Database(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {

            // Attempt to read in a data file
            JsonReader reader = new JsonReader(new FileReader(new File("database.json")));
            Database loadedManager = gson.fromJson(reader, StudyProfile.class);
            // Set study profiles to those loaded in
            studyProfiles = loadedManager.studyProfiles;
            System.out.println("Successfully read in \"database.json\".");

        }
        catch (IOException e){

            // ..else, create a new set of study profiles.
            System.out.println("Failed to read in data. Starting from scratch.");
            studyProfiles = new HashMap<>();
            modules = new HashMap<>();
            deliverables = new HashMap<>();
            studyTasks = new HashMap<>();
            activities = new HashMap<>();
            notes = new HashMap<>();

        }

    }

    public void saveDatabaseToFile(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String databaseJson = gson.toJson(this);

        try {

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

    public boolean containsStudyProfile(UUID uuid){

        return studyProfiles.containsKey(uuid);

    }

    public StudyProfile getStudyProfileFromUUID(UUID uuid) throws IllegalArgumentException{

        if (containsStudyProfile(uuid)){

            return studyProfiles.get(uuid);

        }
        else throw new IllegalArgumentException("StudyProfile " + uuid.toString() + " could not be found.");

    }

    public void addModule(Module module){

        modules.put(module.getID(), module);

    }

    public boolean containsModule(UUID uuid){

        return modules.containsKey(uuid);

    }

    public Module getModuleFromUUID(UUID uuid) throws IllegalArgumentException{

        if(containsModule(uuid)){

            return modules.get(uuid);

        }
        else throw new IllegalArgumentException("Module " + uuid.toString() + " could not be found.");

    }

    public void addDeliverable(Deliverable deliverable){

        deliverables.put(deliverable.getID(), deliverable);

    }

    public boolean containsDeliverable(UUID uuid){

        return deliverables.containsKey(uuid);

    }

    public Deliverable getDeliverableFromUUID(UUID uuid) throws IllegalArgumentException{

        if (containsDeliverable(uuid)){

            return deliverables.get(uuid);

        }
        else throw new IllegalArgumentException("Deliverable " + uuid.toString() + " could not be found.");

    }

    public void addStudyTask(StudyTask studyTask){

        studyTasks.put(studyTask.getID(), studyTask);

    }

    public boolean containsStudyTask(UUID uuid){

        return studyTasks.containsKey(uuid);

    }

    public StudyTask getStudyTaskFromUUID(UUID uuid) throws IllegalArgumentException{

        if (containsStudyTask(uuid)){

            return studyTasks.get(uuid);

        }
        else throw new IllegalArgumentException("StudyTask " + uuid.toString() + " could not be found.");

    }

    public void addActivity(Activity activity){

        activities.put(activity.getID(), activity);

    }

    public boolean containsActivity(UUID uuid){

        return activities.containsKey(uuid);

    }

    public Activity getActivityFromUUID(UUID uuid) throws IllegalArgumentException{

        if (containsActivity(uuid)){

            return activities.get(uuid);

        }
        else throw new IllegalArgumentException("Activity " + uuid.toString() + " could not be found.");

    }

    public void addNote(Note note){

        notes.put(note.getID(), note);

    }

    public boolean containsNote(UUID uuid){

        return notes.containsKey(uuid);

    }

    public Note getNoteFromUUID(UUID uuid) throws IllegalArgumentException{

        if (containsNote(uuid)){

            return notes.get(uuid);

        }
        else throw new IllegalArgumentException("Note " + uuid.toString() + " could not be found.");

    }

}