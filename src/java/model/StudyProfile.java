package java.model;

import java.util.ArrayList;

public class StudyProfile {
    private String month;
    private String year;
    private ArrayList<Module> modules;

    public StudyProfile(String month, String year){
        this.month = month;
        this.year = year;
        this.modules = new ArrayList();
    }

    public String getMonth() { return month; }

    public String getYear() { return year; }

    public ArrayList<Module> getModules() { return modules; }

    public void setModules(ArrayList<Module> modules) { this.modules = modules; }

    public void addModule(Module m){ modules.add(m); }
    public void removeModule(Module m){ modules.remove(m); }

}
