package pl.atom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import pl.atom.links.LinksController;
import pl.atom.utils.FileReaderToText;
import pl.atom.utils.SearchException;
import pl.atom.utils.TxtFileReaderToText;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Klasa która zarządza GUI
 */
public class Controller {
    /**
     * Przycisk pozwalający na sprawdzenie tekstu pod kątem plagiatu.
     */
    public Button checkButton;
    /**
     * Kontener na obiekty graficzne interfejsu użtkownika
     */
    public Pane pane;
    /**
     * Kontener na pole tekstowe pozwalający na przesuwanie.
     */
    public ScrollPane scrollPane;
    /**
     * Kontener na pole Menu
     */
    public MenuBar menuBar;
    /**
     * Element menu odpowiedzialny za otwarcie okna z linkami
     */
    public MenuItem showLinksMenuItem;
    /**
     * Pole tekstowe aplikacji
     */
    public TextArea textField;
    public RadioMenuItem googleRadioItem;
    public RadioMenuItem otherRadioItem;
    /**
     * Główna scena aplikacji
     */
    private Stage stage;
    /**
     * Lista łańcuchów tekstowych odnalezionych w Internecie
     */
    private List<String> negativeStringList;
    /**
     * Pole z klasą dokonującą sprawdzenia tekstu.
     */
    PlagiarismChecker plagiarismChecker;
    public void Controller() {
    }

    /**
     * Metoda zarządzająca obiektami w razie zmiany rozmiaru oknia w poziomie
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
     * Metoda zarządzająca obietakmi w razie zmiany rozmiaru okna w pionie
     */
    private void heightResize() {
        double height = stage.getHeight();
        pane.setPrefHeight(height);
        scrollPane.setPrefHeight(height - checkButton.getHeight() * 3 - menuBar.getHeight());
        textField.setPrefHeight(height - checkButton.getHeight() * 3 - menuBar.getHeight());
        checkButton.setLayoutY(height - checkButton.getHeight() * 3);
    }

    /**
     * Metoda ustawiająca scenę kontrolera. Dodatkowo przypisuje jej operacje zmiany rozmiaru okna.
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
     * Metoda wywołująca sprawdzenie wprowadzonego tekstu oraz wyświetlająca jego wynik dla użytkownika.
     */
    public void check(ActionEvent actionEvent) {
        String text = textField.getText();
        if (text != null && text.length() > 0) {
            try {
                plagiarismChecker = new PlagiarismChecker();
                setSearcher();
                negativeStringList = plagiarismChecker.checkTextForPlagiarism(text);
                int negativePhrasesNumber = plagiarismChecker.getNegativePhrasesNumber();
                int totalPhrasesNumber = plagiarismChecker.getSearchPhrasesNumber();
                if (totalPhrasesNumber > 0) {
                    int percentageValue = ((negativePhrasesNumber * 100) / totalPhrasesNumber);
                    String message = "W wyniku sprawdzenia wykryto, że " + String.valueOf(percentageValue) + "% fraz z tekstu zostało znalezionych za pomocą wyszukiwarki.";
                    createDialog("Wynik sprawdzenia", message);
                    showLinksMenuItem.setDisable(false);
                    textField.setText(plagiarismChecker.getTextToCheck());
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

    private void setSearcher() {
       if(googleRadioItem.isSelected()){
           plagiarismChecker.setGoogleSearcher(100);
       } else {
           createDialog("Nie wspierana wyszukiwarka","Wybrana wyszukiwarka nie jest jeszcze wspierana." +
                   "Zamiast tego użyta zostanie wyszukiwarka testowa.");
       }
    }

    /**
     * Metoda tworząca okno z błędem
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
     * Metoda tworząca okno z informacją
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
     * Metoda zamykająca główną scenę
     */
    public void close(ActionEvent actionEvent) {
        stage.close();
    }

    /**
     * Metoda wyświetlająca nowe okno z linkami do zapytań które zostały odnalezione w trakcie sprawdzania w sieci.
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
        controller.setSearcher(getSearcherName());
        controller.setLinksList(negativeStringList);
        linksStage.setTitle("Linki");
        linksStage.setScene(new Scene(root, 400, 300));
        linksStage.show();
    }

    private String getSearcherName() {
        if(googleRadioItem.isSelected()){
            return "Google";
        } else {
            return "Other";
        }
    }

    /**
     * Metoda wyświetlająca informację o aplikacji
     */
    public void showAbout(ActionEvent actionEvent) {
        String message="Program pozwala na sprawdzenie czy fragmenty pracy pochodzą z tekstów innych prac. W tym" +
                " celu aplikacja wybiera z tekstu frazy do wyszukiwania i następnie sprawdza" +
                "czy wynik wyszukiwania zwraca jakieś wyniki. Jeśli tak fraza taka jest oznaczana jako negatywna" +
                "i następnie użytkownik ma możliwość za pomocą specjalnego okna przejście za pomocą hiperłączy" +
                "do przeglądarki w której wyświetlona zostanie strona z zapytaniem. \n\n Autor: Arutr Tomaszewski.";
        createDialog("Projekt Inżynierski - AntyPlagiat",message);
    }

    /**
     * Metoda wyświetlająca okno pomocy
     */
    public void showHelp(ActionEvent actionEvent) {
        String message="Tekst można wkleić z dowolnego źródła. Może on zawierać obrazki oraz inne obiekty " +
                "jednak należy mieć na uwadze, że nie zostaną one wyświetlone ani sprawdzone, a wszelkie inne obiekty oraz znaki formatujące to utrudniają. " +
                "Można również otwierać pliki tekstowe .txt co może ułatwiać wprowadzenie tekstu."+
                "Dodatkowo ze względu na ograniczenie bezpłatnej wersji nałożony jest limit na 100 wyszukiwań na dzień." +
                "W przypadku, gdy tekst zawierałby więcej niż 100 fraz wyszukiwania wybrane zostanie jedynie 100. " +
                "Po przekroczeniu limitu należy czekać do dnia następnego. Opcja wyświetlenia linków jest dostępna dopiero po przeanalizowaniu jakiegoś tekstu.";
        createDialog("Pomoc", message);
    }

    /**
     * Metoda pozwalająca otworzyć plik i wprowadzić go do pola tekstowego.
     */
    public void openFile(ActionEvent actionEvent) {
        FileChooser fc=new FileChooser();
        fc.setTitle("Select file:");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("any", "*.*"));
        File file=fc.showOpenDialog(this.stage);
        FileReaderToText fileReader;
        if(file!=null){
                if(file.getName().endsWith(".txt")) {
                    fileReader = new TxtFileReaderToText();
                    String text = fileReader.getTextFromFile(file);
                    textField.setText(text);
                } else {
                    createDialog("Format nie wspierany","Format pliku nie jest jeszcze wspierany. Proszę wybrać plik tekstowy.");
                }
        }
    }

    /**
     * Inicjalizacja GUI
     */
    public void initialize() {
        textField.setWrapText(true);
        ToggleGroup group=new ToggleGroup();
        googleRadioItem.setToggleGroup(group);
        otherRadioItem.setToggleGroup(group);
        googleRadioItem.setSelected(true);
    }
}
