package model;

import java.util.UUID;

public class Activity {

    private final String ID;
    private String description;
    private int hoursTaken;


    /**
     * Constructor for an Activity.
     * @param description what the activity consisted of.
     * @param hoursTaken to complete the activity.
     */
    public Activity(String description, int hoursTaken) {

        // Generate random UUID
        ID = UUID.randomUUID().toString();

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
    public String getID() {
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
    public int getHoursTaken() { return this.getHoursTaken(); }

    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addActivity(this);

    }

}
