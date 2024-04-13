package org.example.fakturomat;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class PDFGenerator {
    public static void generatePDF(TextField numerFaktury, DatePicker dataWystawienia, DatePicker dataSprzedazy,
                                   TextField nabywca, TextField nip, TextField ulica, TextField miasto,
                                   ComboBox<String> osobaWystawiajaca,
                                   TextField nazwaTowaru, ComboBox<String> jednostkaMiary,
                                   TextField ilosc, TextField cenaNetto, ComboBox<String> stawkaVAT) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Bardzo ważne, aby plik .ttf był, bo inaczej program nie obsługuje np. kropki i wywala błędy
                contentStream.setFont(PDType0Font.load(document, new File("TimesNewRoman.ttf")), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
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

                contentStream.endText();
            }

            document.save("faktura.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
