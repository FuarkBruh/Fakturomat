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
        TextField nowaNazwaTowaru = new TextField();
        nowaNazwaTowaru.setPrefHeight(nazwaTowaru.getPrefHeight());
        nowaNazwaTowaru.setPrefWidth(nazwaTowaru.getPrefWidth());
        nowaNazwaTowaru.setId("nazwaTowaru_" + UUID.randomUUID());

        ComboBox<String> nowaJednostkaMiary = new ComboBox<>();
        nowaJednostkaMiary.setPrefHeight(jednostkaMiary.getPrefHeight());
        nowaJednostkaMiary.setPrefWidth(jednostkaMiary.getPrefWidth());
        nowaJednostkaMiary.setItems(jednostkaMiary.getItems());
        nowaJednostkaMiary.setId("jednostkaMiary_" + UUID.randomUUID());

        TextField nowaIlosc = new TextField();
        nowaIlosc.setPrefHeight(ilosc.getPrefHeight());
        nowaIlosc.setPrefWidth(ilosc.getPrefWidth());
        nowaIlosc.setId("ilosc_" + UUID.randomUUID());

        TextField nowaCenaNetto = new TextField();
        nowaCenaNetto.setPrefHeight(cenaNetto.getPrefHeight());
        nowaCenaNetto.setPrefWidth(cenaNetto.getPrefWidth());
        nowaCenaNetto.setId("cenaNetto_" + UUID.randomUUID());

        ComboBox<String> nowaStawkaVAT = new ComboBox<>();
        nowaStawkaVAT.setPrefHeight(stawkaVAT.getPrefHeight());
        nowaStawkaVAT.setPrefWidth(stawkaVAT.getPrefWidth());
        nowaStawkaVAT.setId("stawkaVAT_" + UUID.randomUUID());

        // Tworzenie nowego HBox dla kolejnej pozycji towarowej
        HBox nowyPoziomyHBox = new HBox();
        nowyPoziomyHBox.setSpacing(10.0);
        nowyPoziomyHBox.getChildren().addAll(nowaNazwaTowaru, nowaJednostkaMiary, nowaIlosc, nowaCenaNetto, nowaStawkaVAT);

        // Dodanie HBox do VBox
        poziomyVBox.getChildren().add(nowyPoziomyHBox);
    }

    @FXML
    protected void onGenerujButtonClick() {
        PDFGenerator.generatePDF(numerFaktury, dataWystawienia, dataSprzedazy, nabywca, nip, ulica, miasto, osobaWystawiajaca,
                nazwaTowaru, jednostkaMiary, ilosc, cenaNetto, stawkaVAT);
    }
}
