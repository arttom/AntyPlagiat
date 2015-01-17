package pl.atom.utils;

/**
 * Interfejs dla klas wyszukiwaczy
 * Created by Artur Tomaszewski on 2015-01-12.
 */
public interface Searcher {
    /**
     * Metoda zwracająca liczbę wyników dla wyszukiwania
     * @param searchText - wyszukiwana fraza
     * @return - liczba wyników
     * @throws SearchException
     */
    public int getNumberOfResults(String searchText) throws SearchException;
}
