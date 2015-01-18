package pl.atom.links;

/**
 * Interfejs pozwalający na otwieranie linków w przeglądarce
 * Created by Artur Tomaszewski on 2015-01-18.
 */
public interface URLOpener {
    public void openURL(String url) throws NoDesktopSupportedException;
}
