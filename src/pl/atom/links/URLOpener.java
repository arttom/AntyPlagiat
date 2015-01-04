package pl.atom.links;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Klasa otwierająca linki URL za pomocą domyślnej systemowej przeglądarki
 * Created by Artur Tomaszewski on 2014-11-25.
 */
public class URLOpener {
    public static void openURL(String url){
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
