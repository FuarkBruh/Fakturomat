package org.example.fakturomat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("faktura.fxml"));
        AnchorPane root = fxmlLoader.load();
        // Ustawienie koloru tła dla sceny
        root.setStyle("-fx-background-color: lightgrey;");

        Scene scene = new Scene(root, 800, 640);
        stage.setTitle("Facktura");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
