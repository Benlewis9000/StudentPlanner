package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Note {

    @Id
    private UUID ID;
    private String name;
    private String description;
    private LocalDateTime dateCreated;

    /*
     * Empty constructor required for Hibernate
     */
    public Note(){}

    public Note(String name, String description, LocalDateTime dateCreated){

        ID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;

        saveToDatabase();

    }

    public UUID getID() {
        return ID;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateCreated() { return dateCreated; }


    /**
     * Save state of object to database, adding or overwriting corresponding UUID if present.
     */
    public void saveToDatabase(){

        Database.getDatabase().addNote(this);

    }

}
