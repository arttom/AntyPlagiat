package pl.atom.links;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

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
        int i=0;
        for(String s:this.negativeStringList) {
            Hyperlink hyperlink = new Hyperlink(s);
            hyperlink.setPrefHeight(HYPERLINK_HEIGHT);
            final String query="\""+s+"\"";
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) {
                    try {
                        URLOpener.openURL("http://google.pl/search?q="+ URLEncoder.encode(query, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });
            i++;
            linksBox.getChildren().add(hyperlink);
            linksBox.setPrefHeight(i*HYPERLINK_HEIGHT);
        }
    }
}
