package pl.atom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Główna klasa aplikacji ładująca klasę Kontrolera i tworząca scenę.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("frame.fxml"));
        Parent root = loader.load();
        Controller controller=loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("AntyPlagiat");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
