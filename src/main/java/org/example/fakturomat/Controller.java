package org.example.fakturomat;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller {
    @FXML
    public ComboBox<String> statusPlatnosci;
    @FXML
    public DatePicker terminPlatnosci;
    @FXML
    public ComboBox<String> sposobPlatnosci;
    @FXML
    public TextField uwagi;
    @FXML
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
    @FXML
    private Label wyswietlaczNetto;
    @FXML
    private Label wyswietlaczPodatku;
    @FXML
    protected VBox podsumowanie;

    private final List<TextField> listaNazwaTowaru = new ArrayList<>();
    private final List<ComboBox<String>> listaJednostkaMiary = new ArrayList<>();
    private final List<TextField> listaIlosc = new ArrayList<>();
    private final List<TextField> listaCenaNetto = new ArrayList<>();
    private final List<ComboBox<String>> listaStawkaVAT = new ArrayList<>();
    private final Map<String, Double> cenyOrazVat = new HashMap<>();
    private final Map<String, Double> iloscOrazCena = new HashMap<>();
    private static int pozycja = 0;

    // Sekwencyjny numer faktury
    private static int numerFakturySekwencyjny = 1;

    @FXML
    public void initialize() {
        addListeners(cenaNetto, stawkaVAT, jednostkaMiary);
        dataWystawienia.valueProperty().addListener((observable, oldValue, newValue) -> nadanieNumeruFaktury());
        nadanieNumeruFaktury();
    }

    private void addListeners(TextField cenaNettoField, ComboBox<String> stawkaVATBox, ComboBox<String> jednostkaMiary) {
        ChangeListener<Object> listener = (observable, oldValue, newValue) -> {
            dodawanieNettoDlaTejSamejStawki();
            kalkulujSume();
        };

        cenaNettoField.textProperty().addListener(listener);
        stawkaVATBox.valueProperty().addListener(listener);
        jednostkaMiary.valueProperty().addListener(listener);
    }

    @FXML
    protected void dodajPozycje() {
        String idLokalne = String.valueOf(pozycja);
        pozycja++;
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
        nowyPoziomyHBox.setSpacing(10.0);
        nowyPoziomyHBox.getChildren().addAll(nowaNazwaTowaru, nowaJednostkaMiary, nowaIlosc, nowaCenaNetto, nowaStawkaVAT);

        poziomyVBox.getChildren().add(nowyPoziomyHBox);

        addListeners(nowaCenaNetto, nowaStawkaVAT, nowaJednostkaMiary);
        kalkulujSume();
    }

    protected void wyswietlaniePodsumowania(String stawkaVATStr, double cenaNetto, double cenaBrutto) {
        String cenaNettoStr = String.valueOf(cenaNetto);
        String cenaBruttoStr = String.valueOf(cenaBrutto);
        Label nowePodsumowanie = new Label("Stawka VAT: " + stawkaVATStr + ", Netto: " + cenaNettoStr + ", Brutto: " + cenaBruttoStr);
        podsumowanie.getChildren().add(nowePodsumowanie);
    }

    protected void kalkulacjaIlosciTowaru() {

    }

    @FXML
    protected void nadanieNumeruFaktury() {
        LocalDate selectedDate = dataWystawienia.getValue();
        if (selectedDate == null) {
            // Jeśli data nie jest wybrana, ustaw na bieżącą datę
            selectedDate = LocalDate.now();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = selectedDate.format(formatter);

        String numerFakturyString = formattedDate + "/" + numerFakturySekwencyjny;
        numerFaktury.setText(numerFakturyString);

        // Zwiększenie sekwencyjnego numeru faktury
        numerFakturySekwencyjny++;
    }

    protected void wyswietlanieSumyBrutto(String sumaBruttoString) {
        wyswietlaczBrutto.setText(sumaBruttoString);
    }

    protected void wyswietlanieSumyNetto(String sumaNettoString) {
        wyswietlaczNetto.setText(sumaNettoString);
    }

    protected void wyswietlanieSumyPodatkow(String sumaPodatkowString) {
        wyswietlaczPodatku.setText(sumaPodatkowString);
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
        double sumaNetto = 0.0;
        double sumaPodatku = 0.0;

        // Clear previous summary labels
        podsumowanie.getChildren().clear();

        for (Map.Entry<String, Double> entry : cenyOrazVat.entrySet()) {
            String stawkaVAT = entry.getKey();
            double cenaNetto = entry.getValue();
            try {
                double procentVAT = Double.parseDouble(stawkaVAT);
                double podatek = cenaNetto * procentVAT / 100;
                double cenaBrutto = cenaNetto * (1 + procentVAT / 100);

                sumaBrutto += cenaBrutto;
                sumaNetto += cenaNetto;
                sumaPodatku += podatek;

                wyswietlaniePodsumowania(stawkaVAT, cenaNetto, cenaBrutto);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input for stawkaVAT: " + stawkaVAT);
                double cenaBrutto = cenaNetto;
                sumaNetto = cenaNetto;
                sumaBrutto += cenaBrutto;

                wyswietlaniePodsumowania(stawkaVAT, cenaNetto, cenaBrutto);
            }
        }

        System.out.println("Suma brutto dla wszystkich pozycji faktury: " + sumaBrutto);
        String sumaBruttoString = String.valueOf(sumaBrutto);
        System.out.println("Suma netto dla wszystkich pozycji faktury: " + sumaNetto);
        String sumaNettoString = String.valueOf(sumaNetto);
        System.out.println("Suma podatku dla wszystkich pozycji faktury: " + sumaPodatku);
        String sumaPodatkuString = String.valueOf(sumaPodatku);

        wyswietlanieSumyBrutto(sumaBruttoString);
        wyswietlanieSumyNetto(sumaNettoString);
        wyswietlanieSumyPodatkow(sumaPodatkuString);
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
