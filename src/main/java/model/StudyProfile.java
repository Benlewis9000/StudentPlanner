package model;

import java.util.ArrayList;

public class StudyProfile {
    private String month;
    private String year;
    private ArrayList<model.Module> modules;

    public StudyProfile(String month, String year){
        this.month = month;
        this.year = year;
        this.modules = new ArrayList();
    }

    public String getMonth() { return month; }

    public String getYear() { return year; }

    public ArrayList<model.Module> getModules() { return modules; }

    public void setModules(ArrayList<model.Module> modules) { this.modules = modules; }

    public void addModule(model.Module m){ modules.add(m); }
    public void removeModule(model.Module m){ modules.remove(m); }

}
