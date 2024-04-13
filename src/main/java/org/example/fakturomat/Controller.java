package org.example.fakturomat;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    protected void onGenerujButtonClick() {
        PDFGenerator.generatePDF(numerFaktury, dataWystawienia, dataSprzedazy, nabywca, nip, ulica, miasto, osobaWystawiajaca,
                nazwaTowaru, jednostkaMiary, ilosc, cenaNetto, stawkaVAT);
    }
}
