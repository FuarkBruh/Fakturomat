package org.example.fakturomat;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGenerator {
    public static void generatePDF(TextField numerFaktury, DatePicker dataWystawienia, DatePicker dataSprzedazy,
                                   TextField nabywca, TextField nip, TextField ulica, TextField miasto,
                                   ComboBox<String> osobaWystawiajaca, TextField nazwaTowaru, ComboBox<String> jednostkaMiary,
                                   TextField ilosc, TextField cenaNetto, ComboBox<String> stawkaVAT,
                                   ComboBox<String> statusPlatnosci, DatePicker terminPlatnosci, ComboBox<String> sposobPlatnosci,
                                   TextField uwagi, DatePicker dataPlatnosci, List<TextField> listaNazwaTowaru, List<TextField> listaIlosc,
                                   List<ComboBox<String>> listaJednostkaMiary, List<TextField> listaCenaNetto, List<ComboBox<String>> listaStawkaVAT) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType0Font.load(document, new File("TimesNewRoman.ttf")), 12);

            float xStart = 50;
            float yStart = 700;
            float width = 1000;
            float height = 40;

            // Górna linia
            contentStream.moveTo(xStart, yStart);
            contentStream.lineTo(xStart + width, yStart);
            contentStream.stroke();

            // Prawa linia
            contentStream.moveTo(xStart + width, yStart);
            contentStream.lineTo(xStart + width, yStart - height);
            contentStream.stroke();

            // Dolna linia
            contentStream.moveTo(xStart, yStart - height);
            contentStream.lineTo(xStart + width, yStart - height);
            contentStream.stroke();

            // Lewa linia
            contentStream.moveTo(xStart, yStart);
            contentStream.lineTo(xStart, yStart - height);
            contentStream.stroke();
            contentStream.beginText();

            contentStream.newLineAtOffset(xStart, yStart);

            contentStream.showText("Numer faktury: " + numerFaktury.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Data wystawienia: " + dataWystawienia.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Data sprzedaży: " + dataSprzedazy.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Nabywca: " + nabywca.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("NIP: " + nip.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Ulica: " + ulica.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Miasto: " + miasto.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Osoba wystawiająca: " + osobaWystawiajaca.getValue());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Nazwa towaru: " + nazwaTowaru.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Jednostka miary: " + jednostkaMiary.getValue());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Ilość: " + ilosc.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Cena netto: " + cenaNetto.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Stawka VAT: " + stawkaVAT.getValue());

            for (int i = 0; i < listaNazwaTowaru.size(); i++) {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Nazwa towaru: " + listaNazwaTowaru.get(i).getText());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Jednostka miary: " + listaJednostkaMiary.get(i).getValue());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ilość: " + listaIlosc.get(i).getText());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Cena netto: " + listaCenaNetto.get(i).getText());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Stawka VAT: " + listaStawkaVAT.get(i).getValue());
            }
            contentStream.endText();
            contentStream.close();

            document.save("faktura.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}