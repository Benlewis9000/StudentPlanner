package model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Module {

    @Id
    private UUID ID;
    private String moduleTitle;
    private String moduleOrganiser;
    private String moduleCode;
    @ElementCollection
    private Set<UUID> deliverableIDs;

    /*
     * Empty constructor required for Hibernate
     */
    public Module(){};

    public Module(String moduleTitle, String moduleOrganiser, String moduleCode){

        this.ID = UUID.randomUUID();
        this.moduleTitle = moduleTitle;
        this.moduleOrganiser = moduleOrganiser;
        this.moduleCode = moduleCode;

        this.deliverableIDs = new HashSet<>();

        saveToDatabase();

    }

    /**
     * Get the UUID for the instance.
     * @return unique ID as UUID.
     */
    public UUID getID() {

        return ID;

    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public String getModuleOrganiser() {
        return moduleOrganiser;
    }

    public String getModuleCode() {
        return moduleCode;
    }


    /**
     * Query whether the Module has the Deliverable corresponding to the given UUID.
     * @param uuid UUID of the Deliverable in question.
     * @return true if the UUID corresponds to a Deliverable owned by this Module.
     */
    public boolean hasDeliverable(UUID uuid){

        return deliverableIDs.contains(uuid);

    }

    /**
     * Get the Deliverable instance of a study task held by the Module.
     * @param uuid of the Deliverable held.
     * @return the instance of the Deliverable.
     * @throws IllegalArgumentException if the Deliverable was not held, or could not be found in the database.
     */
    public Deliverable getDeliverableFromUUID(UUID uuid) throws IllegalArgumentException{

        if (hasDeliverable(uuid)){

            return Database.getDatabase().getDeliverableFromUUID(uuid);

        }
        else throw new IllegalArgumentException("Deliverable " + uuid + " is not a member of Module + " + this.getID() + ".");

    }

    /**
     * Add a Deliverable to the Module.
     * @param deliverable to add.
     */
    public void addDeliverable(Deliverable deliverable){

        deliverableIDs.add(deliverable.getID());

    }

    /**
     * Remove a Deliverable from the Module (thus delete from database).
     * @param deliverable to remove.
     */
    public void removeDeliverable(Deliverable deliverable){

        deliverableIDs.remove(deliverable.getID());
        Database.getDatabase().deleteDeliverable(deliverable.getID());

    }

    /**
     * Get all IDs of Deliverables owned by this Module.
     * @return set of UUIDs.
     */
    public Set<UUID> getDeliverableIDs() {
        return deliverableIDs;
    }

    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addModule(this);

    }

    @Override
    public String toString(){

        return getModuleCode();

    }

}
