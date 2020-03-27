package model;

import java.time.LocalDateTime;

public class Note {

    private String name;
    private String description;
    private LocalDateTime dateCreated;


    public Note(String name, String description, LocalDateTime dateCreated){
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateCreated() { return dateCreated; }


    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
