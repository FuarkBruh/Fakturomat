package org.example.fakturomat;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class Controller {
    public ComboBox statusPlatnosci;
    public DatePicker terminPlatnosci;

    public ComboBox <String> sposobPlatnosci;
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
    protected void dodajPozycje() {
        String idLokalne = String.valueOf(UUID.randomUUID());
        // Tworzenie nowych kontrolki i nadawanie im unikatowych fx:id
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

        // Dodanie pary cen netto i stawek VAT do mapy
        String stawkaVATValue = String.valueOf(stawkaVAT.getValue());
        String cenaNettoS = cenaNetto.getText();
        if(cenaNettoS != null) {
            double cenaNettoValue = Double.parseDouble(cenaNettoS);
            if (cenyOrazVat.containsKey(stawkaVATValue)) {
                // Aktualizacja sumy cen netto dla danej stawki VAT
                double sumaNetto = cenyOrazVat.get(stawkaVATValue);
                sumaNetto += cenaNettoValue;
                cenyOrazVat.put(stawkaVATValue, sumaNetto);
            } else {
                // Dodanie nowej sumy cen netto dla danej stawki VAT
                cenyOrazVat.put(stawkaVATValue, cenaNettoValue);
            }
        }

        // Tworzenie nowego HBox dla kolejnej pozycji towarowej
        HBox nowyPoziomyHBox = new HBox();
        nowyPoziomyHBox.setSpacing(10.0);
        nowyPoziomyHBox.getChildren().addAll(nowaNazwaTowaru, nowaJednostkaMiary, nowaIlosc, nowaCenaNetto, nowaStawkaVAT);

        // Dodanie HBox do VBox
        poziomyVBox.getChildren().add(nowyPoziomyHBox);

        kalkulujSume();
    }

    protected void kalkulujSume() {
        double sumaBrutto = 0.0;

        for (Map.Entry<String, Double> entry : cenyOrazVat.entrySet()) {
            String stawkaVAT = entry.getKey();
            double cenaNetto = entry.getValue();

            // Oblicz wartość brutto dla danej stawki VAT
            double procentVAT = Double.parseDouble(stawkaVAT); // Usuń znak procentu i przekonwertuj na double
            double cenaBrutto = cenaNetto * (1 + procentVAT / 100);

            // Dodaj wartość brutto do sumy
            sumaBrutto += cenaBrutto;
        }

        // Tutaj możesz zrobić coś z sumą brutto, np. wyświetlić ją w interfejsie użytkownika lub zapisać do jakiejś zmiennej
        System.out.println("Suma brutto dla wszystkich pozycji faktury: " + sumaBrutto);
        String sumaBruttoString = String.valueOf(sumaBrutto);
        wyswietlaczBrutto.setText(sumaBruttoString);
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