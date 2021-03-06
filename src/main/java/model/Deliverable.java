package model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
public class Deliverable {

    @Id
    private UUID ID;
    private DeliverableType TYPE;
    private String title;
    private String description;
    private LocalDate deadline;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UUID> studyTaskIDs;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UUID> noteIDs;
    private boolean isSummative;

    /*
     * Empty constructor required for Hibernate
     */
    public Deliverable(){}

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

    public boolean isSummative() {
        return isSummative;
    }

    public Set<UUID> getStudyTaskIDs() {
        return studyTaskIDs;
    }

    public Set<UUID> getNoteIDs() {
        return noteIDs;
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

    @Override
    public String toString(){

        return this.title;

    }

    /**
     * Generate a dummy instance for testing with set values.
     * @return instance of Deliverable.
     */
    public static Deliverable generateDummy(){

        return new Deliverable(DeliverableType.EXAM, "Final Exam", "50% exam for module. 09:00 in C. HALL.", LocalDate.of(2020, 6, 12), true);

    }

}
