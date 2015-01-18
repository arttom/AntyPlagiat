package pl.atom.links;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Klasa otwierająca linki URL za pomocą domyślnej systemowej przeglądarki w systemach Windows oraz Gnome
 * Created by Artur Tomaszewski on 2014-11-25.
 */
public class WindowsAndGnomeURLOpener implements URLOpener{
    /**
     * Metoda otwierająca link za pomocą domyślnej przeglądarki systemowej.
     * @param url - łańcuch tekstowy zawierający link
     */
    @Override
    public void openURL(String url) throws NoDesktopSupportedException {
        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else throw new NoDesktopSupportedException("Niewspierana konfiguracja systemowa.");
    }
}
