package model;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.UUID;

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

    private final UUID ID;
    private final TaskType TYPE;
    private final int HOURS_REQUIRED;
    private HashSet<UUID> dependencyIDs;
    private HashSet<UUID> activityIDs;
    private HashSet<UUID> noteIDs;

    /**
     * Constructor for StudyTask.
     * @param taskType the type of task.
     * @param hoursRequired to complete the task.
     * @param dependencyIDs UUIDs of StudyTasks that must be completed before this one can begin.
     * @param activityIDs UUIDs of Activities that this task consists of.
     * @param noteIDs UUIDs of additional notes.
     */
    public StudyTask(TaskType taskType, int hoursRequired, HashSet<UUID> dependencyIDs, HashSet<UUID> activityIDs, HashSet<UUID> noteIDs){

        this.ID = UUID.randomUUID();
        this.TYPE = taskType;
        this.HOURS_REQUIRED = hoursRequired;
        this.dependencyIDs = dependencyIDs;
        this.activityIDs = activityIDs;
        this.noteIDs = noteIDs;

    }

    /**
     * Empty constructor (minus type).
     * @param taskType the type of task.
     * @param hoursRequired to complete the task.
     */
    public StudyTask(TaskType taskType, int hoursRequired){

        this(taskType, hoursRequired, new HashSet<UUID>(), new HashSet<UUID>(), new HashSet<UUID>());

    }


    public UUID getID() {
        return ID;
    }

    public TaskType getType() {
        return TYPE;
    }

    public int getHoursRequired(){
        return HOURS_REQUIRED;
    }


    /**
     * Query whether the StudyTask has a dependency on the StudyTask given.
     * @param studyTask the dependency in question.
     * @return true if this study task is dependent on the study task given.
     */
    public boolean hasStudyTaskDependency(StudyTask studyTask){

        return hasStudyTaskDependency(studyTask.getID());

    }

    /**
     * Query whether the UUID of a StudyTask is a dependency.
     * @param uuid of the dependency.
     * @return true if this study task is dependent on that of the uuid given.
     */
    public boolean hasStudyTaskDependency(UUID uuid){

        return this.noteIDs.contains(uuid);

    }

    /**
     * Get the StudyTask instance of a dependency.
     * @param uuid of the dependency study task.
     * @return the instance of the StudyTask.
     * @throws IllegalArgumentException if the StudyTask is not a dependency or could not be found.
     */
    public StudyTask getStudyTaskDependencyFromUUID(UUID uuid) throws IllegalArgumentException{

        if (hasStudyTaskDependency(uuid)){

            return Database.getDatabase().getStudyTaskFromUUID(uuid);

        }
        else throw new IllegalArgumentException("StudyTask " + uuid + " is not a dependency of StudyTask + " + this.getID() + ".");

    }

    /**
     * Add a StudyTask dependency to the StudyTask.
     * @param studyTask to be dependent on.
     */
    public void addStudyTaskDependency(StudyTask studyTask){

        // Prevent a dependency on itself
        if (studyTask.getID() != this.getID())
            dependencyIDs.add(studyTask.getID());

    }

    /**
     * Remove a study task dependency.
     * @param studyTask dependency to remove.
     */
    public void removeStudyTaskDependency(StudyTask studyTask){

        dependencyIDs.remove(studyTask.getID());

    }

    /**
     * Query whether the study task has the activity corresponding to the given UUID.
     * @param uuid UUID of the Activity in question.
     * @return true if the UUID corresponds to an activity owned by this StudyTask.
     */
    public boolean hasActivity(UUID uuid){

        return this.activityIDs.contains(uuid);

    }

    /**
     * Get the Activity instance of an activity owned by the StudyTask.
     * @param uuid of the Activity owned.
     * @return the instance of the Activity.
     * @throws IllegalArgumentException if the Activity was not owned, or could not be found in the database.
     */
    public Activity getActivityFromUUID(UUID uuid) throws IllegalArgumentException{

        // Check StudyTask is an owner of the Activity
        if (hasActivity(uuid)){

            return Database.getDatabase().getActivityFromUUID(uuid);

        }
        else throw new IllegalArgumentException("Activity " + uuid + " is not a member of StudyTask + " + this.getID() + ".");

    }

    /**
     * Add an activity to the StudyTask.
     * @param activity to add.
     */
    public void addActivity(Activity activity){

        activityIDs.add(activity.getID());

    }

    /**
     * Remove and activity from the StudyTask.
     * @param activity to remove.
     */
    public void removeActivity(Activity activity){

        activityIDs.remove(activity.getID());

    }


}

