package java.model;
import java.util.Date;
import java.util.ArrayList;

public class Deliverable {
    public enum deliverableTYPE{
        EXAM("exam"), COURSEWORK("coursework"), MILESTONE("milestone");
        private String displayName;
        deliverableTYPE(String displayName){
            this.displayName = displayName;
        }

    }
    private deliverableTYPE deliverableType;
    private String title;
    private String description;
    private Date deadline;
    private ArrayList<StudyTask> studyTasks;
    private ArrayList<Note> notes;
    private boolean isSummative;
    private boolean isComplete;

    public Deliverable(deliverableTYPE deliverableType, String title, String description, Date deadline, boolean isSummative){
        this.deliverableType = deliverableType;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isSummative = isSummative;

        this.studyTasks = new ArrayList();
        this.notes = new ArrayList();
    }

    public deliverableTYPE getDeliverableType() {
        return deliverableType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
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

    public void setDeliverableType(deliverableTYPE deliverableType) {
        this.deliverableType = deliverableType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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

    public void setSummative(boolean summative) {
        isSummative = summative;
    }


    public void addStudyTask(StudyTask x){studyTasks.add(x);}
    public void removeStudyTask(StudyTask x){studyTasks.add(x);}

    public void addNote(Note x){notes.add(x);}
    public void removeNote(Note x){notes.remove(x);}


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
