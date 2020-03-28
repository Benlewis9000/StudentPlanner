package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface JSONSerializable {

    default public String toJSON(){

        System.out.println("toJSON()");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(this));

        return gson.toJson(this);

    }

}
