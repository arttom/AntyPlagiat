package pl.atom.utils;

/**
 * Interfejs dla klas wyszukiwaczy
 * Created by Artur Tomaszewski on 2015-01-12.
 */
public interface Searcher {
    public int getNumberOfResults(String searchText) throws SearchException;
}
