package model;

import java.util.ArrayList;

public class Module {

    private final String moduleTitle;
    private final String moduleOrganiser;
    private final String moduleCode;
    private ArrayList<Deliverable> deliverables;


    public Module(String moduleTitle, String moduleOrganiser, String moduleCode){

        this.moduleTitle = moduleTitle;
        this.moduleOrganiser = moduleOrganiser;
        this.moduleCode = moduleCode;
        this.deliverables = new ArrayList<>();

    }


    public void addDeliverable(Deliverable x){this.deliverables.add(x);}

    public void removeStudyTask(Deliverable x){this.deliverables.remove(x);}

    public String getModuleTitle() {
        return moduleTitle;
    }

    public String getModuleOrganiser() {
        return moduleOrganiser;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public ArrayList<Deliverable> getDeliverables() {
        return deliverables;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleTitle='" + moduleTitle + '\'' +
                ", moduleOrganiser='" + moduleOrganiser + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", deliverables=" + deliverables +
                '}';
    }
}
