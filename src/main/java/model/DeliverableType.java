package model;

public enum DeliverableType{

    EXAM("exam"),
    COURSEWORK("coursework"),
    MILESTONE("milestone");

    private String name;

    DeliverableType(String name){

        this.name = name;
    }

}
