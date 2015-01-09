package pl.atom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.controlsfx.dialog.Dialogs;
import pl.atom.links.LinksController;
import pl.atom.utils.SearchException;
import org.apache.pdfbox.util.PDFTextStripper;
import pl.atom.utils.TxtFileReaderToText;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa która zarządza GUI
 */
public class Controller {
    public Button checkButton;
    public Pane pane;
    public ScrollPane scrollPane;
    public MenuBar menuBar;
    public MenuItem showLinksMenuItem;
    public TextArea textField;
    private Stage stage;
    private List<String> negativeStringList;

    public void Controller() {

    }

    /**
     * Funkcja zarządzająca obiektami w razie zmiany rozmiaru oknia w poziomie
     */
    private void widthResize() {
        double width = stage.getWidth();
        pane.setPrefWidth(width);
        scrollPane.setPrefWidth(width);
        textField.setPrefWidth(width - 15);
        checkButton.setLayoutX(width / 2 - checkButton.getWidth() / 2);
        menuBar.setPrefWidth(width);

    }

    /**
     * Funkcja zarządzająca obietakmi w razie zmiany rozmiaru okna w pionie
     */
    private void heightResize() {
        double height = stage.getHeight();
        pane.setPrefHeight(height);
        scrollPane.setPrefHeight(height - checkButton.getHeight() * 3 - menuBar.getHeight());
        textField.setPrefHeight(height - checkButton.getHeight() * 3 - menuBar.getHeight());
        checkButton.setLayoutY(height - checkButton.getHeight() * 3);
    }

    /**
     * Fukcja ustawiająca scenę kontrolera. Dodatkowo przypisuje jej operacje zmiany rozmiaru okna.
     * @param stage - Główna scena aplikacji
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                heightResize();
            }
        });
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                widthResize();
            }
        });
        stage.setMinWidth(300);
        stage.setMinHeight(300);
    }

    /**
     * Funkcja wywołująca sprawdzenie wprowadzonego tekstu oraz wyświetlająca jego wynik dla użytkownika.
     * @param actionEvent
     */
    public void check(ActionEvent actionEvent) {
        String text = textField.getText();
        if (text != null && text.length() > 0) {
            PlagiarismChecker plagiarismChecker = new PlagiarismChecker();
            try {
                negativeStringList = plagiarismChecker.checkTextForPlagiarism(text);
                int negativePhrasesNumber = plagiarismChecker.getNegativePhrasesNumber();
                int totalPhrasesNumber = plagiarismChecker.getSearchPhrasesNumber();
                if (totalPhrasesNumber > 0) {
                    int percentageValue = ((negativePhrasesNumber * 100) / totalPhrasesNumber);
                    String message = "W wyniku sprawdzenia wykryto, że " + String.valueOf(percentageValue) + "% fraz z tekstu zostało znalezionych za pomocą wyszukiwarki.";
                    createDialog("Wynik sprawdzenia", message);
                    showLinksMenuItem.setDisable(false);
                }
                else{
                    createDialog("Brak fraz do wyszukiwania", "Na podstawie wprowadzonego tekstu nie utworzono żadnych fraz do wyszukania");
                }
            } catch (SearchException e) {
                createErrorDialog("Error", e.getMessage());
            }
        } else {
            createDialog("Brak tekstu", "Brak wprowadzonego tekstu do sprawdzenia");
        }
    }

    /**
     * Utworzenie okna z błędem
     * @param title - tytuł okna
     * @param message - wiadomość okna
     */
    private void createErrorDialog(String title, String message) {
        Dialogs.create()
                .owner(stage)
                .title(title)
                .masthead(null)
                .message(message)
                .showError();
    }

    /**
     * Utworzenie okna z informacją
     * @param title - tytuł okna
     * @param message - wiadomość okna
     */
    private void createDialog(String title, String message) {

        Dialogs.create()
                .owner(stage)
                .title(title)
                .masthead(null)
                .message(message)
                .showInformation();
    }

    /**
     * zamknięcie głównej sceny
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        stage.close();
    }

    /**
     * Funkcja wyświetlająca nowe okno z linkami do zapytań które zostały odnalezione w trakcie sprawdzania w sieci.
     * @param actionEvent
     */
    public void showLinks(ActionEvent actionEvent) {
        Stage linksStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("links/linksFrame.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinksController controller = loader.getController();
        controller.setLinksList(negativeStringList);
        linksStage.setTitle("Linki");
        linksStage.setScene(new Scene(root, 400, 300));
        linksStage.show();
    }

    /**
     * Wyświetlenie informacji o aplikacji
     * @param actionEvent
     */
    public void showAbout(ActionEvent actionEvent) {
        //TODO
        String message="Program pozwala na sprawdzenie czy fragmenty pracy pochodzą z tekstów innych prac. W tym" +
                " celu aplikacja wybiera z tekstu frazy do wyszukiwania i następnie sprawdza" +
                "czy wynik wyszukiwania zwraca jakieś wyniki. Jeśli tak fraza taka jest oznaczana jako negatywna" +
                "i następnie użytkownik ma możliwość za pomocą specjalnego okna przejście za pomocą hiperłączy" +
                "do przeglądarki w której wyświetlona zostanie strona z zapytaniem. \n\n Autor: Arutr Tomaszewski.";
        createDialog("Projekt Inżynierski - AntyPlagiat",message);
    }

    /**
     * Wyświetlenie okna pomocy
     * @param actionEvent
     */
    public void showHelp(ActionEvent actionEvent) {
        String message="Tekst można wprowadzić z dowolnego źródła. Może on zawierać obrazki oraz inne obiekty " +
                "jednak należy mieć na uwadze, że aplikacja sprawdza jedynie tekst, a wszelkie inne obiekty oraz znaki formatujące to utrudniają. "+
                "Dodatkowo ze względu na ograniczenie bezpłatnej wersji nałożony jest limit na 100 wyszukiwań na dzień." +
                "W przypadku, gdy tekst zawierałby więcej niż 100 fraz wyszukiwania wybrane zostanie jedynie 100. " +
                "Po przekroczeniu limitu należy czekać do dnia następnego. Opcja wyświetlenia linków jest dostępna dopiero po przeanalizowaniu jakiegoś tekstu.";
        createDialog("Pomoc", message);
    }

    public void openFile(ActionEvent actionEvent) {
        FileChooser fc=new FileChooser();
        fc.setTitle("Select TXT file");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file=fc.showOpenDialog(this.stage);
        if(file!=null){
                TxtFileReaderToText txtReader=new TxtFileReaderToText();
                String text=txtReader.getTextFromFile(file);
                textField.setText(text);
        }
    }
}
