module com.uacm.estimacionifpug.estimacionproyectosifpug {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.uacm.estimacionifpug.estimacionproyectosifpug to javafx.fxml;
    exports com.uacm.estimacionifpug.estimacionproyectosifpug;
}