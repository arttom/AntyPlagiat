package pl.atom.links;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Kontroller GUI z linkami
 * Created by Artur Tomaszewski on 2014-11-25.
 */
public class LinksController {

    public VBox linksBox;
    private double HYPERLINK_HEIGHT=24;
    List<String> negativeStringList;
    public void setLinksList(List<String> negativeStringsList){
        this.negativeStringList=negativeStringsList;
        createHyperlinks();
    }

    /**
     * Utworzenie hiperłączy do google na GUI
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
