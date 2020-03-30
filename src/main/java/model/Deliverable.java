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
    private transient HashSet<StudyTask> studyTasks;
    private transient HashSet<Note> notes;
    private final boolean isSummative;
    private transient boolean isComplete;     // Todo: how is completion set/calculated?


    public Deliverable(DeliverableType deliverableType, String title, String description, LocalDate deadline, boolean isSummative){

        ID = UUID.randomUUID();
        this.TYPE = deliverableType;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isSummative = isSummative;

        this.studyTasks = new HashSet<StudyTask>();
        this.notes = new HashSet<Note>();

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

    public HashSet<StudyTask> getStudyTasks() {
        return studyTasks;
    }

    public HashSet<Note> getNotes() {
        return notes;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isSummative() {
        return isSummative;
    }


    // todo: will need to use database? YES
    public void addStudyTask(StudyTask task){this.studyTasks.add(task);}
    public void removeStudyTask(StudyTask task){this.studyTasks.remove(task);}

    public void addNote(Note note){notes.add(note);}
    public void removeNote(Note note){notes.remove(note);}

    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addDeliverable(this);

    }


    /**
     * Generate a dummy instance for testing with set values.
     * @return instance of Deliverable.
     */
    public static Deliverable generateDummy(){

        return new Deliverable(DeliverableType.EXAM, "Final Exam", "50% exam for module. 09:00 in C. HALL.", LocalDate.of(2020, 6, 12), true);

    }

}
