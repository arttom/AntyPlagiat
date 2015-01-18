package pl.atom.links;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialogs;
import pl.atom.utils.Searchers;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Kontroller GUI z linkami
 * Created by Artur Tomaszewski on 2014-11-25.
 */
public class LinksController {
    /**
     * Kontener graficzny do umieszczenia hiperłączy
     */
    public VBox linksBox;
    private final double HYPERLINK_HEIGHT=24;
    /**
     * Lista łańcuchów tekstowych zawierających teksty fraz które zostały odnalezione w Internecie
     */
    private List<String> negativeStringList;
    private Searchers searcherType;

    /**
     * Setter ustawiający linki
     * @param negativeStringsList - lista łańcuchów tekstowych zawierających frazy odnalezione w Internecie
     */
    public void setLinksList(List<String> negativeStringsList){
        this.negativeStringList=negativeStringsList;
        createHyperlinks();
    }

    /**
     * Metoda tworząca hiperłącza do google na GUI.
     */
    private void createHyperlinks() {
        switch(searcherType) {
            case GOOGLE:
            int i = 0;
            for (String s : this.negativeStringList) {
                Hyperlink hyperlink = new Hyperlink(s);
                hyperlink.setPrefHeight(HYPERLINK_HEIGHT);
                final String query = "\"" + s + "\"";
                hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        URLOpener urlOpener;
                        try {
                            if(Desktop.isDesktopSupported()) urlOpener=new WindowsAndGnomeURLOpener();
                            else urlOpener=new MacOSorLinuxURLOpener();
                            urlOpener.openURL("http://google.pl/search?q=" + URLEncoder.encode(query, "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (NoDesktopSupportedException e) {
                            e.printStackTrace();
                            Dialogs.create()
                                    .owner(this)
                                    .title("Niewspierany system")
                                    .masthead(null)
                                    .message("Konfiguracja systemowa nie pozwala na otwarcie przeglądarki.")
                                    .showError();
                        }
                    }
                });
                i++;
                linksBox.getChildren().add(hyperlink);
                linksBox.setPrefHeight(i * HYPERLINK_HEIGHT);
            }
            break;
            default:
               Label label = new Label();
               label.setText("Nie wspierana wyszukiwarka");
               linksBox.getChildren().add(label);
        }
    }

    /**
     * Pozwala ustawić wyszukiwarkę do której generowane są linki
     * @param searcherName
     */
    public void setSearcher(String searcherName) {
        if(searcherName.equals("Google")){
            this.searcherType=Searchers.GOOGLE;
        } else this.searcherType=Searchers.NONE;
    }
}
