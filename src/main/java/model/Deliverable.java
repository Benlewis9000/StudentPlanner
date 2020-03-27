package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Deliverable {

    public enum DeliverableType{

        EXAM("exam"),
        COURSEWORK("coursework"),
        MILESTONE("milestone");

        private String name;

        DeliverableType(String name){

            this.name = name;
        }

        @Override
        public String toString() {
            return "DeliverableType{" +
                    "name='" + name + '\'' +
                    '}';
        }

    }


    private final DeliverableType TYPE;
    private String title;
    private String description;
    private LocalDate deadline;
    private ArrayList<StudyTask> studyTasks;
    private ArrayList<Note> notes;
    private final boolean isSummative;
    private boolean isComplete;     // Todo: how is completion set/calculated?


    public Deliverable(DeliverableType deliverableType, String title, String description, LocalDate deadline, boolean isSummative){

        this.TYPE = deliverableType;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isSummative = isSummative;

        this.studyTasks = new ArrayList<StudyTask>();
        this.notes = new ArrayList<Note>();

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

    public ArrayList<StudyTask> getStudyTasks() {
        return studyTasks;
    }

    public ArrayList<Note> getNotes() {
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

    public void setStudyTasks(ArrayList<StudyTask> studyTasks) {
        this.studyTasks = studyTasks;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }


    public void addStudyTask(StudyTask task){this.studyTasks.add(task);}
    public void removeStudyTask(StudyTask task){this.studyTasks.remove(task);}

    public void addNote(Note note){notes.add(note);}
    public void removeNote(Note note){notes.remove(note);}


    @Override
    public String toString() {
        return "Deliverable{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", studyTasks=" + studyTasks +
                ", notes=" + notes +
                ", isSummative=" + isSummative +
                ", isComplete=" + isComplete +
                '}';
    }
}
