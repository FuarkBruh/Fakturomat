package org.example.fakturomat;

import javafx.scene.control.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGenerator {
    public static void generatePDF(TextField numerFaktury, DatePicker dataWystawienia, DatePicker dataSprzedazy, DatePicker dataPlatnosci,
                                   TextField nabywca, TextField nip, TextField ulica, TextField miasto, TextField kodPocztowy, ComboBox<String> statusPlatnosci,
                                   ComboBox<String> osobaWystawiajaca, DatePicker terminPlatnosci, ComboBox<String> sposobPlatnosci,
                                   TextField uwagi, List<TextField> listaNazwaTowaru, List<TextField> listaIlosc,
                                   List<ComboBox<String>> listaJednostkaMiary, List<TextField> listaCenaNetto, List<ComboBox<String>> listaStawkaVAT, List<Label> listaIloscRazyBrutto) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType0Font.load(document, new File("TimesNewRoman.ttf")), 12);

            float xStart = 0;
            float yStart = 1000;
            float width = 1000;
            float height = 1000;

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

            contentStream.newLineAtOffset(225, 700);

            contentStream.showText("Numer faktury: " + numerFaktury.getText());
            contentStream.newLineAtOffset(200, 40);
            contentStream.showText("Data wystawienia: " + dataWystawienia.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(-200, -70);
            contentStream.showText("Data sprzedaży: " + dataSprzedazy.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Płatne do: " + terminPlatnosci.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Sposób płatności: " + sposobPlatnosci.getValue());
            contentStream.newLineAtOffset(200, -25);
            contentStream.showText("Nabywca: " + nabywca.getText());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("NIP: " + nip.getText());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Ulica: " + ulica.getText());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Miasto: " + miasto.getText());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Kod pocztowy: " + kodPocztowy.getText());
            contentStream.newLineAtOffset(-400, -300);
            contentStream.showText("Osoba wystawiająca: " + osobaWystawiajaca.getValue());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Uwagi: " + uwagi.getText());




            int initialX = 55;
            int initialY = 260;
            contentStream.newLineAtOffset(initialX, initialY);

            for (int i = 0; i < listaNazwaTowaru.size(); i++) {
                if (i % 3 == 0 && i != 0) {
                    // Nowa linia po każdej trzeciej iteracji
                    contentStream.newLineAtOffset(-(6 * initialX), -40); // Resetowanie X i przesunięcie w dół
                } else if (i != 0) {
                    // Przesunięcie w prawo po każdej iteracji poza pierwszą
                    contentStream.newLineAtOffset(initialX*3, 0);
                }

                contentStream.showText("Nazwa towaru: " + listaNazwaTowaru.get(i).getText());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Jednostka miary: " + listaJednostkaMiary.get(i).getValue());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Ilość: " + listaIlosc.get(i).getText());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Cena jednostkowa netto: " + listaCenaNetto.get(i).getText());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Stawka VAT: " + String.valueOf(listaStawkaVAT.get(i).getValue()));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Całość za towar: " + listaIloscRazyBrutto.get(i).getText());

                // Po ostatnim produkcie w linii wracamy na początkową pozycję Y
                if ((i + 1) % 3 != 0) {
                    contentStream.newLineAtOffset(0, 75);
                }
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