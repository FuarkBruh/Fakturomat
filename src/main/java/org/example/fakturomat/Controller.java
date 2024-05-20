package org.example.fakturomat;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class Controller {
    public ComboBox statusPlatnosci;
    public DatePicker terminPlatnosci;
    public ComboBox<String> sposobPlatnosci;
    public TextArea uwagi;
    public DatePicker dataPlatnosci;
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
    private Label wyswietlaczBrutto;
    private final List<TextField> listaNazwaTowaru = new ArrayList<>();
    private final List<ComboBox<String>> listaJednostkaMiary = new ArrayList<>();
    private final List<TextField> listaIlosc = new ArrayList<>();
    private final List<TextField> listaCenaNetto = new ArrayList<>();
    private final List<ComboBox<String>> listaStawkaVAT = new ArrayList<>();
    private final Map<String, Double> cenyOrazVat = new HashMap<>();

    @FXML
    public void initialize() {
        addListeners(cenaNetto, stawkaVAT);
    }

    private void addListeners(TextField cenaNettoField, ComboBox<String> stawkaVATBox) {
        ChangeListener<Object> listener = (observable, oldValue, newValue) -> {
            dodawanieNettoDlaTejSamejStawki();
            kalkulujSume();
        };

        cenaNettoField.textProperty().addListener(listener);
        stawkaVATBox.valueProperty().addListener(listener);
    }

    @FXML
    protected void dodajPozycje() {
        String idLokalne = String.valueOf(UUID.randomUUID());

        TextField nowaNazwaTowaru = new TextField();
        nowaNazwaTowaru.setPrefHeight(nazwaTowaru.getPrefHeight());
        nowaNazwaTowaru.setPrefWidth(nazwaTowaru.getPrefWidth());
        nowaNazwaTowaru.setId("nazwaTowaru_" + idLokalne);
        listaNazwaTowaru.add(nowaNazwaTowaru);

        ComboBox<String> nowaJednostkaMiary = new ComboBox<>();
        nowaJednostkaMiary.setPrefHeight(jednostkaMiary.getPrefHeight());
        nowaJednostkaMiary.setPrefWidth(jednostkaMiary.getPrefWidth());
        nowaJednostkaMiary.setItems(jednostkaMiary.getItems());
        nowaJednostkaMiary.setId("jednostkaMiary_" + idLokalne);
        listaJednostkaMiary.add(nowaJednostkaMiary);

        TextField nowaIlosc = new TextField();
        nowaIlosc.setPrefHeight(ilosc.getPrefHeight());
        nowaIlosc.setPrefWidth(ilosc.getPrefWidth());
        nowaIlosc.setId("ilosc_" + idLokalne);
        listaIlosc.add(nowaIlosc);

        TextField nowaCenaNetto = new TextField();
        nowaCenaNetto.setPrefHeight(cenaNetto.getPrefHeight());
        nowaCenaNetto.setPrefWidth(cenaNetto.getPrefWidth());
        nowaCenaNetto.setId("cenaNetto_" + idLokalne);
        listaCenaNetto.add(nowaCenaNetto);

        ComboBox<String> nowaStawkaVAT = new ComboBox<>();
        nowaStawkaVAT.setPrefHeight(stawkaVAT.getPrefHeight());
        nowaStawkaVAT.setPrefWidth(stawkaVAT.getPrefWidth());
        nowaStawkaVAT.setItems(stawkaVAT.getItems());
        nowaStawkaVAT.setId("stawkaVAT_" + idLokalne);
        listaStawkaVAT.add(nowaStawkaVAT);

        HBox nowyPoziomyHBox = new HBox();
        nowyPoziomyHBox.setSpacing(0.0);
        nowyPoziomyHBox.getChildren().addAll(nowaNazwaTowaru, nowaJednostkaMiary, nowaIlosc, nowaCenaNetto, nowaStawkaVAT);

        poziomyVBox.getChildren().add(nowyPoziomyHBox);

        addListeners(nowaCenaNetto, nowaStawkaVAT);
        kalkulujSume();
    }

    protected void wyswietlanieSumyBrutto(String sumaBruttoString) {
        wyswietlaczBrutto.setText(sumaBruttoString);
    }

    protected void wyswietlanieSumyNetto() {

    }

    protected void wyswietlaniePodsumNetto() {

    }

    protected void wyswietlaniePodsumBrutto() {

    }

    protected void dodawanieNettoDlaTejSamejStawki() {
        cenyOrazVat.clear(); // Clear the map to recalculate
        for (int i = 0; i < listaNazwaTowaru.size(); i++) {
            String stawkaVATValue = String.valueOf(listaStawkaVAT.get(i).getValue());
            String cenaNettoS = listaCenaNetto.get(i).getText();
            if (stawkaVATValue != null && cenaNettoS != null && !cenaNettoS.isEmpty()) {
                double cenaNettoValue = Double.parseDouble(cenaNettoS);
                cenyOrazVat.merge(stawkaVATValue, cenaNettoValue, Double::sum);
            }
        }
        System.out.println("Ceny oraz vat: " + cenyOrazVat);
    }

    protected void kalkulujSume() {
        double sumaBrutto = 0.0;

        for (Map.Entry<String, Double> entry : cenyOrazVat.entrySet()) {
            String stawkaVAT = entry.getKey();
            double cenaNetto = entry.getValue();

            try {
                double procentVAT = Double.parseDouble(stawkaVAT);
                double cenaBrutto = cenaNetto * (1 + procentVAT / 100);

                sumaBrutto += cenaBrutto;
            }
            catch (NumberFormatException e) {
                System.err.println("Invalid input for stawkaVAT: " + stawkaVAT);
            }
        }

        System.out.println("Suma brutto dla wszystkich pozycji faktury: " + sumaBrutto);
        String sumaBruttoString = String.valueOf(sumaBrutto);
        wyswietlanieSumyBrutto(sumaBruttoString);
    }

    @FXML
    protected void onGenerujButtonClick() {
        PDFGenerator.generatePDF(
                numerFaktury, dataWystawienia, dataSprzedazy, nabywca, nip, ulica, miasto, osobaWystawiajaca,
                nazwaTowaru, jednostkaMiary, ilosc, cenaNetto, stawkaVAT, statusPlatnosci, terminPlatnosci,
                sposobPlatnosci, uwagi, dataPlatnosci, listaNazwaTowaru,
                listaIlosc, listaJednostkaMiary, listaCenaNetto, listaStawkaVAT);
    }
}
