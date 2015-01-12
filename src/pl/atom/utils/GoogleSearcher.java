package pl.atom.utils;

/**
 * Klasa implementująca interfejs Searcher do wyszukiwania zapytań w google
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

/**
 * Klasa pozwalająca na wyszukiwanie za pomocą wyszukiwarki google
 */
public class GoogleSearcher implements Searcher{
    /**
     * Prywatny klucz do API Google
     */
    final private static String APIKey="AIzaSyAjrogGT62RwW6f6WEjSMGUFGrrFKilzOw";
    /**
     * ID wyszukiwarki Google.
     */
    final private static String customSearchID ="010644167715613412296:izqrbfcvv9m";

    /**
     * Metoda zwracajaca liczbę wyników zapytania. (maksymalnie 10 ze względu na limit API ale wystarcza)
     * @param searchText - wyszukiwana fraza
     * @return - liczbę wyników zapytania
     * @throws SearchException - w przypadku błędu przy wyszukiwaniu
     */
    @Override
    public int getNumberOfResults(String searchText) throws SearchException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        Customsearch customsearch = new Customsearch(httpTransport, jsonFactory,null);
        List<Result> resultList = null;
        try {
            Customsearch.Cse.List list = customsearch.cse().list(searchText);
            list.setKey(APIKey);
            list.setCx(customSearchID);
            list.setStart(10L);
            Search results = list.execute();
            resultList = results.getItems();
        }catch (Exception e) {
            throw new SearchException("Error during search operation. Please Repeat. If this doesn't help you probably used daily limit. If tommorow error still occurs. Please contact author.");
        }
        return resultList.size();
    }
}
