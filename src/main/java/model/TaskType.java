package model;

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
