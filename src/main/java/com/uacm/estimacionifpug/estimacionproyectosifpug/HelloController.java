package com.uacm.estimacionifpug.estimacionproyectosifpug;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class HelloController {

    @FXML private TextField numeroElementosFuncionales;
    @FXML private TextField numeroElementosNoFuncionales;
    @FXML private VBox containerFuncionales;
    @FXML private VBox containerNoFuncionales;

    @FXML
    protected void onGenerarFuncionesDatosClick() {
        generarTarjetas(numeroElementosFuncionales, containerFuncionales, "Funciones de datos");
    }

    @FXML
    protected void onGenerarFuncionesTransaccionesClick() {
        generarTarjetas(numeroElementosNoFuncionales, containerNoFuncionales, "Funciones de transacciones");
    }

    private void generarTarjetas(TextField input, VBox container, String tipo) {
        container.getChildren().clear();
        try {
            int cantidad = Integer.parseInt(input.getText());
            for (int i = 1; i <= cantidad; i++) {
                container.getChildren().add(crearTarjetaEditable(tipo + " " + i));
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa un número válido");
        }
    }

    private VBox crearTarjetaEditable(String tituloDefecto) {
        VBox tarjeta = new VBox(8);
        tarjeta.setPadding(new Insets(10));
        tarjeta.setStyle("-fx-border-color: #abb2b9; -fx-border-radius: 5; -fx-background-color: white;");

        // Campo para el nombre
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del elemento");
        txtNombre.setText(tituloDefecto);

        tarjeta.getChildren().addAll(new Label("Nombre:"), txtNombre);
        return tarjeta;
    }
}