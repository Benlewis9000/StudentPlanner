package model;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Activity {

    @Id
    private UUID ID;
    private String description;
    private int hoursTaken;

    /*
     * Empty constructor required for Hibernate
     */
    public Activity(){}

    /**
     * Constructor for an Activity.
     * @param description what the activity consisted of.
     * @param hoursTaken to complete the activity.
     */
    public Activity(String description, int hoursTaken) {

        // Generate random UUID
        ID = UUID.randomUUID();

        // Make sure hours set is positive and not 0
        if (hoursTaken < 0) hoursTaken = 1;

        this.description = description;
        this.hoursTaken = hoursTaken;

        saveToDatabase();

    }

    /**
     * Get the UUID for the instance.
     * @return unique ID as UUID.
     */
    public UUID getID() {
        return this.ID;
    }

    /**
     * Accessor for description.
     * @return description.
     */
    public String getDescription() { return this.description; }

    /**
     * Accessor for hoursTaken.
     * @return the hours taken to complete the Activity.
     */
    public int getHoursTaken() { return this.hoursTaken; }

    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addActivity(this);

    }

    @Override
    public String toString(){

        return getDescription();

    }

}
