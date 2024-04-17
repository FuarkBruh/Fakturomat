package org.example.fakturomat;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class Controller {
    @FXML
    private TextField numerFaktury;
    @FXML
    private DatePicker dataWystawienia;
    @FXML
    private DatePicker dataSprzedazy;
    @FXML
    private TextField nabywca;
    @FXML
    private TextField nip;
    @FXML
    private TextField ulica;
    @FXML
    private TextField miasto;
    @FXML
    private ComboBox<String> osobaWystawiajaca;
    @FXML
    private TextField nazwaTowaru;
    @FXML
    private ComboBox<String> jednostkaMiary;
    @FXML
    private TextField ilosc;
    @FXML
    private TextField cenaNetto;
    @FXML
    private ComboBox<String> stawkaVAT;
    @FXML
    private VBox poziomyVBox;
    @FXML
    private HBox poziomyHBox;
    @FXML
    protected void dodajPozycje() {
        // Tworzenie nowych kontrolki i nadawanie im unikatowych fx:id
        TextField nazwaTowaru = new TextField();
        nazwaTowaru.setId("nazwaTowaru_" + UUID.randomUUID().toString());

        ComboBox<String> jednostkaMiary = new ComboBox<>();
        jednostkaMiary.setId("jednostkaMiary_" + UUID.randomUUID().toString());

        TextField ilosc = new TextField();
        ilosc.setId("ilosc_" + UUID.randomUUID().toString());

        TextField cenaNetto = new TextField();
        cenaNetto.setId("cenaNetto_" + UUID.randomUUID().toString());

        ComboBox<String> stawkaVAT = new ComboBox<>();
        stawkaVAT.setId("stawkaVAT_" + UUID.randomUUID().toString());

        // Tworzenie nowego HBox dla kolejnej pozycji towarowej
        HBox nowyPoziomyHBox = new HBox();
        nowyPoziomyHBox.getChildren().addAll(nazwaTowaru, jednostkaMiary, ilosc, cenaNetto, stawkaVAT);

        // Dodanie HBox do VBox
        poziomyVBox.getChildren().add(nowyPoziomyHBox);
    }




    @FXML
    protected void onGenerujButtonClick() {
        PDFGenerator.generatePDF(numerFaktury, dataWystawienia, dataSprzedazy, nabywca, nip, ulica, miasto, osobaWystawiajaca,
                nazwaTowaru, jednostkaMiary, ilosc, cenaNetto, stawkaVAT);
    }
}
