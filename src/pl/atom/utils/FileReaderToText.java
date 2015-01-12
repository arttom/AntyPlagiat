package pl.atom.utils;

import java.io.File;

/**
 * Interfejs dla klas otwierających pliki.
 * Created by Artur Tomaszewski on 2014-12-22.
 */
public interface FileReaderToText {
    /**
     * Metoda otwierająca plik zawierających tekst za pomocą obiektu klasy File
     * @param file - obiekt klasy File
     * @return - łańcuch tekstowy z pliku
     */
    public String getTextFromFile(File file);

    /**
     * Metoda otwierająca plik zawierający tekst za pomocą ścieżki do niego
     * @param path - ścieżka do pliku
     * @return - łańcuch tekstowy z pliku
     */
    public String getTextFromFile(String path);
}
