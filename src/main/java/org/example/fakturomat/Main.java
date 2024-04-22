package org.example.fakturomat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("faktura.fxml"));
        GridPane root = fxmlLoader.load();

        // Pobierz szerokość i wysokość ekranu
        double screenWidth = stage.getWidth();
        double screenHeight = stage.getHeight();

        // Ustaw proporcjonalne położenie elementów na ekranie
        root.setPrefWidth(screenWidth);
        root.setPrefHeight(screenHeight);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Faktura");

        // Ustaw kolor tła dla sceny
        scene.getRoot().setStyle("-fx-background-color: lightgrey;");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
