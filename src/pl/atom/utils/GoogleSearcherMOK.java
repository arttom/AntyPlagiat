package pl.atom.utils;

/**
 * Klasa do wyszukiwania zapytań w google
 * Created by Artur Tomaszewski on 2014-11-20.
 */
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.util.List;
import java.util.Random;

public class GoogleSearcherMOK {
    final private static String APIKey="AIzaSyAjrogGT62RwW6f6WEjSMGUFGrrFKilzOw";
    final private static String customSearchID ="010644167715613412296:izqrbfcvv9m";
    final private String GOOGLE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1?";

    /**
     * Zaślepka do testów zamiast getNumberOfResult
     * @param s - wyszukiwany tekst
     * @return - losowa wartość określająca liczbę znalezonych zapytań
     * @throws pl.atom.utils.SearchException - nie wyrzuca. Dodane aby było tożsame z getNumberOfResult
     */
    public static int getNumberOfResultsDumb(String s) throws SearchException{
        Random random=new Random();
        return random.nextInt(2);
    }
}
