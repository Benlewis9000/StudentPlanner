package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Database {


    // Singleton pattern reference
    private static final Database DATABASE = initialiseDatabase();

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


    private Database(){

        studyProfiles = new HashMap<>();
        modules = new HashMap<>();
        deliverables = new HashMap<>();
        studyTasks = new HashMap<>();
        activities = new HashMap<>();
        notes = new HashMap<>();

        // activities = loadEntities(Activity.class, em);

    }

    public HashMap<UUID, StudyProfile> getStudyProfiles() {
        return studyProfiles;
    }

    public HashMap<UUID, Module> getModules() {
        return modules;
    }

    public HashMap<UUID, Deliverable> getDeliverables() {
        return deliverables;
    }

    public HashMap<UUID, StudyTask> getStudyTasks() {
        return studyTasks;
    }

    public HashMap<UUID, Activity> getActivities() {
        return activities;
    }

    public HashMap<UUID, Note> getNotes() {
        return notes;
    }

    private static Database initialiseDatabase(){

        Database db = new Database();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("p-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<StudyProfile> eStudyProfiles = em.createQuery("SELECT e FROM StudyProfile e", StudyProfile.class).getResultList();
        List<Module> eModules = em.createQuery("SELECT e FROM Module e", Module.class).getResultList();
        List<Deliverable> eDeliverables = em.createQuery("SELECT e FROM Deliverable e", Deliverable.class).getResultList();
        List<StudyTask> eStudyTasks = em.createQuery("SELECT e FROM StudyTask e", StudyTask.class).getResultList();
        List<Activity> eActivities = em.createQuery("SELECT e FROM Activity e", Activity.class).getResultList();
        List<Note> eNotes = em.createQuery("SELECT e FROM Note e", Note.class).getResultList();

        eStudyProfiles.forEach((e) -> db.studyProfiles.put(e.getID(), e));
        eModules.forEach((e) -> db.modules.put(e.getID(), e));
        eDeliverables.forEach((e) -> db.deliverables.put(e.getID(), e));
        eStudyTasks.forEach((e) -> db.studyTasks.put(e.getID(), e));
        eActivities.forEach((e) -> db.activities.put(e.getID(), e));
        eNotes.forEach((e) -> db.notes.put(e.getID(), e));

        em.getTransaction().commit();

        return db;
    }

    public void saveDatabase(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("p-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        this.studyProfiles.forEach((id, e) -> em.merge(e));
        this.modules.forEach((id, e) -> em.merge(e));
        this.deliverables.forEach((id, e) -> em.merge(e));
        this.studyTasks.forEach((id, e) -> em.merge(e));
        this.activities.forEach((id, e) -> em.merge(e));
        this.notes.forEach((id, e) -> em.merge(e));

        em.getTransaction().commit();
        em.close();
        emf.close();

    }

    // Todo: alter such that it reads objects in from tables in database
    @Deprecated
    private static Database loadDatabaseFromFile(){

        Gson gson = new Gson();

        try {

            // Attempt to read in a data file
            JsonReader reader = new JsonReader(new FileReader(new File("database.json")));
            System.out.println("Successfully read in \"database.json\".");

            Database database = gson.fromJson(reader, Database.class);
            System.out.println("Successfully parsed \"database.json\".");

            // Deserialize json and return Database instance
            return database;

        }
        catch (Exception e){

            // ..else, create a new set of study profiles.
            System.out.println("Failed to read in data. Starting from scratch.");
            return new Database();

        }

    }

    /**
     * Serialize and write out the current database state to "database.json".
     */
    // Todo: edit so saves objects as rows in relations in database
    @Deprecated
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

    /**
     * Export a StudyProfile out as a semester profile (to be used by hub)
     * @param studyProfile containing relevant modules and deliverables.
     */
    // Todo: should stay?
    public static void exportAsSemesterProfile(StudyProfile studyProfile){

        // Database to hold data to export
        Database exportDatabase = new Database();

        // Add semesters study profile to database
        exportDatabase.studyProfiles.put(studyProfile.getID(), studyProfile);

        // Iterate modules owned by study profile and add to database
        for (UUID moduleUUID : studyProfile.getModuleIDs()){

            exportDatabase.modules.put(moduleUUID, getDatabase().modules.get(moduleUUID));

            // Iterate deliverables owned by each module and add to database
            for (UUID deliverableUUID : getDatabase().modules.get(moduleUUID).getDeliverableIDs()){

                exportDatabase.deliverables.put(deliverableUUID, getDatabase().deliverables.get(deliverableUUID));

            }

        }

        // Format file name
        int startYear = studyProfile.getStartYear().getValue();
        int endYear = startYear + 1;
        String fileName = startYear + "-" + endYear + "-" + studyProfile.getSemester() + ".json";

        // Serialize export database to json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String semesterProfileJson = gson.toJson(exportDatabase, Database.class);

        // Write json out to file
        try {

            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
            writer.write(semesterProfileJson);
            writer.close();


        }
        catch (IOException e){

            e.printStackTrace();

        }

    }

    /**
     * Import a semester profile file.
     * @param path to semester profile.
     * @return whether import was successful.
     */
    public static boolean importSemesterProfile(String path){

        // Database to import into
        Database importedDatabase;

        Gson gson = new Gson();

        try {

            // Attempt to read in a data file
            JsonReader reader = new JsonReader(new FileReader(new File(path)));
            System.out.println("Successfully read in " + path + ".");

            // Deserialize json to get Database instance
            importedDatabase = gson.fromJson(reader, Database.class);

        }
        catch (Exception e){

            System.out.println("Error: failed to import Semester Profile from " + path + ".");
            return false;

        }

        // Merge data into main database
        getDatabase().studyProfiles.putAll(importedDatabase.studyProfiles);
        getDatabase().modules.putAll(importedDatabase.modules);
        getDatabase().deliverables.putAll(importedDatabase.deliverables);

        return true;

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

    public void deleteStudyProfile(UUID uuid){

        studyProfiles.remove(uuid);

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

    public void deleteModule(UUID uuid){

        modules.remove(uuid);

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

    public void deleteDeliverable(UUID uuid){

        deliverables.remove(uuid);

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

    public void deleteStudyTask(UUID uuid){

        studyTasks.remove(uuid);

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

    public void deleteActivity(UUID uuid){

        activities.remove(uuid);

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

    public void deleteNote(UUID uuid){

        notes.remove(uuid);

    }

}
