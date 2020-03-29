package controller;

import model.StudyProfile;

public class Main {


    // Singleton reference to this instance (the application)
    public static final Main APPLICATION = new Main();


    public StudyProfileController studyProfileController;


    private Main(){

        this.studyProfileController = null;

    }


    // Main method for application
    public static void main(String[] args){



    }

}
