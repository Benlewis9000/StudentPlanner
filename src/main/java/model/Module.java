package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class Module {

    private final UUID ID;
    private final String moduleTitle;
    private final String moduleOrganiser;
    private final String moduleCode;
    private HashSet<Deliverable> deliverables;


    public Module(String moduleTitle, String moduleOrganiser, String moduleCode){

        this.ID = UUID.randomUUID();
        this.moduleTitle = moduleTitle;
        this.moduleOrganiser = moduleOrganiser;
        this.moduleCode = moduleCode;
        this.deliverables = new HashSet<>();

        saveToDatabase();

    }

    /**
     * Get the UUID for the instance.
     * @return unique ID as UUID.
     */
    public UUID getID() {

        return ID;

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

    public HashSet<Deliverable> getDeliverables() {
        return deliverables;
    }

    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addModule(this);

    }

}
