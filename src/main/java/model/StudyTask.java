package model;

import java.util.ArrayList;
import java.util.HashSet;

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

    public final TaskType TYPE;
    public final int HOURS_REQUIRED;
    HashSet<StudyTask> dependencies;
    HashSet<Activity> activities;
    HashSet<Note> notes;

    /**
     * Constructor for StudyTask.
     * @param taskType the type of task.
     * @param hoursRequired to complete the task.
     * @param dependencies StudyTasks that must be completed before this one can begin.
     * @param activities that this task consists of.
     * @param notes additional notes.
     */
    public StudyTask(TaskType taskType, int hoursRequired, HashSet<StudyTask> dependencies, HashSet<Activity> activities, HashSet<Note> notes){

        this.TYPE = taskType;
        this.HOURS_REQUIRED = hoursRequired;
        this.dependencies = dependencies;
        this.activities = activities;
        this.notes = notes;

    }

    /**
     * Empty constructor (minus type).
     * @param taskType the type of task.
     * @param hoursRequired to complete the task.
     */
    public StudyTask(TaskType taskType, int hoursRequired){

        this(taskType, hoursRequired, new HashSet<StudyTask>(), new HashSet<Activity>(), new HashSet<Note>());

    }

}
