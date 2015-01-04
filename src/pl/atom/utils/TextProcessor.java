package pl.atom.utils;

import org.jsoup.Jsoup;

/**
 * Klasa która przetwarza tekst
 * Created by Artur Tomaszewski on 2014-11-20.
 */
public class TextProcessor {
    /**
     * Funkcja parsująca tekst z HTMLa na czysty tekst
     * @param htmlText - tekst ze znacznikami html
     * @return - tekst bez znaczników html
     */
    public static String htmlTextToString(String htmlText){
        return Jsoup.parse(htmlText).text();
    }
}
