package model;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public class Tests {

    public static void main(String[] args){

        testGson();
        System.out.println();

        testJSONSerializable();
        System.out.println();

    }

    /**
     * First test to check whether GSON is set up and works.
     */
    public static void testGson(){

        Note note = new Note("Test Gson", "This is a test for Gson.", LocalDateTime.now());

        Gson gson = new Gson();
        String noteJson = gson.toJson(note);
        System.out.println(noteJson);

        try {

            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("note.json"), "utf-8"));

            writer.write(noteJson);

            writer.close();


        }
        catch (IOException e){

            e.printStackTrace();

        }

    }

    /**
     * Test the JSONSerializable interface and default method.
     */
    public static void testJSONSerializable(){

        Note note = new Note("Test JSONSerializable", "This is a test for JSONSerializable.", LocalDateTime.now());

        String noteJson = note.toJSON();

        System.out.println(noteJson);

    }

}
