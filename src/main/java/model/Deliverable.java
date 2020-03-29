package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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


    private final DeliverableType TYPE;
    private String title;
    private String description;
    private LocalDate deadline;
    private transient HashSet<StudyTask> studyTasks;
    private transient HashSet<Note> notes;
    private final boolean isSummative;
    private transient boolean isComplete;     // Todo: how is completion set/calculated?


    public Deliverable(DeliverableType deliverableType, String title, String description, LocalDate deadline, boolean isSummative){

        this.TYPE = deliverableType;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isSummative = isSummative;

        this.studyTasks = new HashSet<StudyTask>();
        this.notes = new HashSet<Note>();

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

    // Todo: why only allow MILESTONEs to be set?
    public void setTitle(String title) {
        if (this.TYPE.equals(DeliverableType.MILESTONE)) {
            this.title = title;
        }
    }

    public void setDescription(String description) {
        if (this.TYPE.equals(DeliverableType.MILESTONE)) {
            this.description = description;
        }
    }

    public void setDeadline(LocalDate deadline) {
        if (this.TYPE.equals(DeliverableType.MILESTONE)) {
            this.deadline = deadline;
        }
    }

    public void setStudyTasks(HashSet<StudyTask> studyTasks) {
        this.studyTasks = studyTasks;
    }

    public void setNotes(HashSet<Note> notes) {
        this.notes = notes;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }


    public void addStudyTask(StudyTask task){this.studyTasks.add(task);}
    public void removeStudyTask(StudyTask task){this.studyTasks.remove(task);}

    public void addNote(Note note){notes.add(note);}
    public void removeNote(Note note){notes.remove(note);}


    /**
     * Generate a dummy instance for testing with set values.
     * @return instance of Deliverable.
     */
    public static Deliverable generateDummy(){

        return new Deliverable(DeliverableType.EXAM, "Final Exam", "50% exam for module. 09:00 in C. HALL.", LocalDate.of(2020, 6, 12), true);

    }

}
