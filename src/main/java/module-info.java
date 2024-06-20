module org.example.fakturomat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.apache.pdfbox;
    requires java.sql;

    opens org.example.fakturomat to javafx.fxml;
    exports org.example.fakturomat;
}