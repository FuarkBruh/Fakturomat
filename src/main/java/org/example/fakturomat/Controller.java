package org.example.fakturomat;

import javafx.fxml.FXML;
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
    protected void onGenerujButtonClick() {
        PDFGenerator.generatePDF(numerFaktury, dataWystawienia, dataSprzedazy, nabywca, nip, ulica, miasto);
    }
}
