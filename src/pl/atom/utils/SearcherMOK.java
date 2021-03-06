package pl.atom.utils;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.util.List;
import java.util.Random;

/**
 * Klasa implementująca interfejs Searcher zastępująca klasy Searcher wyszukującą w testach.
 */
public class SearcherMOK implements Searcher{
    /**
     * Zaślepka do testów klas implementujących Searcher
     * @param s - wyszukiwany tekst
     * @return - losowa wartość określająca liczbę znalezonych zapytań
     * @throws pl.atom.utils.SearchException - nie wyrzuca. Dodane aby było tożsame z getNumberOfResult
     */
    @Override
    public int getNumberOfResults(String s) throws SearchException{
        Random random=new Random();
        return random.nextInt(2);
        }
}
