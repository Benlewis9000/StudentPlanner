package tests;

import com.google.gson.Gson;
import model.Activity;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class GsonTest {

    /**
     * First test to check whether GSON is set up and works.
     */
    @Test
    public void testGson(){

        Activity activity = new Activity("Test Gson", 123456789);

        Gson gson = new Gson();
        String noteJson = gson.toJson(activity);
        System.out.println(noteJson);

        try {

            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test-activity.json"), "utf-8"));

            writer.write(noteJson);

            writer.close();

            Reader reader = new FileReader("test-note.json");
            Activity noteIn = gson.fromJson(reader, Activity.class);

            assertEquals(activity.getID(), noteIn.getID());

        }
        catch (IOException e){

            e.printStackTrace();

        }

    }

}
