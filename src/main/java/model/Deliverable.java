package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Deliverable {

    public enum DeliverableType{

        EXAM("exam"),
        COURSEWORK("coursework"),
        MILESTONE("milestone");

        private String name;

        DeliverableType(String name){

            this.name = name;
        }

    }


    private final UUID ID;
    private final DeliverableType TYPE;
    private String title;
    private String description;
    private LocalDate deadline;
    private transient HashSet<UUID> studyTaskIDs;
    private transient HashSet<UUID> noteIDs;
    private final boolean isSummative;
    private transient boolean isComplete;     // Todo: how is completion set/calculated?


    public Deliverable(DeliverableType deliverableType, String title, String description, LocalDate deadline, boolean isSummative){

        ID = UUID.randomUUID();
        this.TYPE = deliverableType;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isSummative = isSummative;

        this.studyTaskIDs = new HashSet<UUID>();
        this.noteIDs = new HashSet<UUID>();

        saveToDatabase();

    }


    /**
     * Get the UUID for the instance.
     * @return unique ID as UUID.
     */
    public UUID getID() {
        return ID;
    }

    public DeliverableType getType () {
        return TYPE;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isSummative() {
        return isSummative;
    }


    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addDeliverable(this);

    }


    /**
     * Query whether the Deliverable has the StudyTask corresponding to the given UUID.
     * @param uuid UUID of the StudyTask in question.
     * @return true if the UUID corresponds to a StudyTask owned by this Deliverable.
     */
    public boolean hasStudyTask(UUID uuid){

        return studyTaskIDs.contains(uuid);

    }

    /**
     * Get the StudyTask instance of a study task held by the Deliverable.
     * @param uuid of the StudyTask held.
     * @return the instance of the StudyTask.
     * @throws IllegalArgumentException if the StudyTask was not held, or could not be found in the database.
     */
    public StudyTask getStudyTaskFromUUID(UUID uuid) throws IllegalArgumentException{

        if (hasStudyTask(uuid)){

            return Database.getDatabase().getStudyTaskFromUUID(uuid);

        }
        else throw new IllegalArgumentException("StudyTask " + uuid + " is not a member of Deliverable + " + this.getID() + ".");

    }

    /**
     * Add a StudyTask to the Deliverable.
     * @param studyTask to add.
     */
    public void addStudyTask(StudyTask studyTask){

        studyTaskIDs.add(studyTask.getID());

    }

    /**
     * Remove a StudyTask from the Deliverable (thus delete from database).
     * @param studyTask to remove.
     */
    public void removeStudyTask(StudyTask studyTask){

        studyTaskIDs.remove(studyTask.getID());
        Database.getDatabase().deleteStudyTask(studyTask.getID());

    }


    /**
     * Query whether the Deliverable has the Note corresponding to the given UUID.
     * @param uuid UUID of the Note in question.
     * @return true if the UUID corresponds to a Note owned by this Deliverable.
     */
    public boolean hasNote(UUID uuid){

        return noteIDs.contains(uuid);

    }

    /**
     * Get the Note instance of an note held by the Deliverable.
     * @param uuid of the Note held.
     * @return the instance of the Note.
     * @throws IllegalArgumentException if the Note was not held, or could not be found in the database.
     */
    public Note getNoteFromUUID(UUID uuid) throws IllegalArgumentException{

        if (hasNote(uuid)){

            return Database.getDatabase().getNoteFromUUID(uuid);

        }
        else throw new IllegalArgumentException("Note " + uuid + " is not a member of Deliverable + " + this.getID() + ".");

    }

    /**
     * Add a Note to the Deliverable.
     * @param note to add.
     */
    public void addNote(Note note){

        noteIDs.add(note.getID());

    }

    /**
     * Remove a Note from the Deliverable (and delete from database).
     * @param note to remove.
     */
    public void removeNote(Note note){

        noteIDs.remove(note.getID());
        Database.getDatabase().deleteNote(note.getID());

    }


    /**
     * Generate a dummy instance for testing with set values.
     * @return instance of Deliverable.
     */
    public static Deliverable generateDummy(){

        return new Deliverable(DeliverableType.EXAM, "Final Exam", "50% exam for module. 09:00 in C. HALL.", LocalDate.of(2020, 6, 12), true);

    }

}
