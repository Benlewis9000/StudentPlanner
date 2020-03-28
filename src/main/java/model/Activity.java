package model;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Activity {

    private String description;
    private int hoursTaken;


    /**
     * Constructor for an Activity.
     * @param description what the activity consisted of.
     * @param hoursTaken to complete the activity.
     * @throws InvalidArgumentException if description contains commas, or hoursTaken is negative.
     */
    public Activity(String description, int hoursTaken) throws InvalidArgumentException{

        if (hoursTaken < 0) throw new IllegalArgumentException("Cannot set negative hours for an Activity.");

        if (description.contains(",")) throw new IllegalArgumentException("Text may not include commas.");

        this.description = description;
        this.hoursTaken = hoursTaken;

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

    @Override
    public String toString(){

        // Todo - decide on format or straight CSV? or use GSON to get as a line
        return null;

    }

}
