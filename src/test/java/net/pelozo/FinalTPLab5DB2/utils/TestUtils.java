package net.pelozo.FinalTPLab5DB2.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.pelozo.FinalTPLab5DB2.model.Client;

import java.util.List;

public class TestUtils {

    public static String aClientJSON(){
        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(aClient());
    }


    public static Client aClient(){
        Client c = new Client();

        c.setDni("39170489");
        c.setEmail("martincaminero64@gmail.com");
        c.setFirstName("martin");
        c.setLastName("caminero");
        c.setResidence(List.of());
        c.setPassword("1234");
        c.setUsername("martin64");

        return c;
    }
}
