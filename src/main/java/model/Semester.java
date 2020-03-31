package model;

public enum Semester {

    AUTUMN("autumn"),
    SPRING("spring");

    public final String name;

    Semester (String name){

        this.name = name;

    }

    @Override
    public String toString(){

        return this.name.toUpperCase();

    }

}
