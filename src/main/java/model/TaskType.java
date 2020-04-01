package model;

/**
 * Enum to state what type of task a StudyTask is.
 */
public enum TaskType {

    REVISION("Revising"),
    PROGRAMMING("Programming"),
    WRITING("Writing"),
    RESEARCH("Research"),
    OTHER("Other");

    String name;

    TaskType(String name){

        this.name = name;

    }

    @Override
    public String toString(){

        return this.name;

    }

}
