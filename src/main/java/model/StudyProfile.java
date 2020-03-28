package model;

import java.time.Year;
import java.util.ArrayList;

public class StudyProfile {


    private final Semester semester;
    private Year startYear;
    private ArrayList<Module> modules;


    public StudyProfile(Semester semester, Year startYear, ArrayList<Module> modules){

        this.semester = semester;
        this.startYear = startYear;
        this.modules = modules;

    }

    public Year getStartYear() { return startYear; }

    public ArrayList<Module> getModules() { return modules; }


    public void setModules(ArrayList<Module> modules) {

        this.modules = modules;

    }

    // Todo - controller or model?
    public void addModule(Module m){

        modules.add(m);

    }

    public void removeModule(Module m){

        modules.remove(m);

    }

}
