package java.model;

import java.util.ArrayList;

public class StudyTask {

    /**
     * Enum to state what type of task a StudyTask is.
     */
    enum TaskType {

        REVISION("Revising"),
        PROGRAMMING("Programming"),
        WRITING("Writing"),
        RESEARCH("Research");

        String name;

        TaskType(String name){

            name = name;

        }

        @Override
        public String toString(){

            return this.name;

        }

    }

    TaskType taskType;
    ArrayList<StudyTask> dependencies;
    ArrayList<Activity> activities;
    ArrayList<Note> notes;

    /**
     * Constructor for StudyTask.
     * @param taskType the type of task.
     * @param dependencies StudyTasks that must be completed before this one can begin.
     * @param activities that this task consists of.
     * @param notes additional notes.
     */
    public StudyTask(TaskType taskType, ArrayList<StudyTask> dependencies, ArrayList<Activity> activities, ArrayList<Note> notes){

        this.taskType = taskType;
        this.dependencies = dependencies;
        this.activities = activities;
        this.notes = notes;

    }

    /**
     * Empty constructor (minus type).
     * @param taskType the type of task.
     */
    public StudyTask(TaskType taskType){

        this(taskType, new ArrayList<StudyTask>(), new ArrayList<Activity>(), new ArrayList<Note>());

    }

}
