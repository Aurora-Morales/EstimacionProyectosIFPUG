package com.uacm.estimacionifpug.estimacionproyectosifpug;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class HelloController {

    @FXML private TextField numeroFuncionesDatosILF;
    private int numero_funciones_datosILF;
    @FXML private TextField numeroFuncionesDatosEIF;
    private int numero_funciones_datosEIF;
    @FXML private TextField numeroFuncionesTransaccionesEI;
    @FXML private TextField numeroFuncionesTransaccionesEO;
    @FXML private TextField numeroFuncionesTransaccionesEQ;
    @FXML private VBox containerFuncionesDatosILF;
    @FXML private VBox containerFuncionesDatosEIF;
    @FXML private VBox containerFuncionesTransaccionesEI;
    @FXML private VBox containerFuncionesTransaccionesEO;
    @FXML private VBox containerFuncionesTransaccionesEQ;

    //Acciones para agregar las tarjetas de las funciones de datos ILF y EIF
    @FXML
    protected void onGenerarFuncionesDatosILFClick() {
        generarTarjetas(numeroFuncionesDatosILF, containerFuncionesDatosILF, "Usuarios");
        numero_funciones_datosILF = Integer.parseInt(numeroFuncionesDatosILF.getText());
        //System.out.println("ILF: " + numero_funciones_datosILF);
    }

    @FXML
    protected void onGenerarFuncionesDatosEIFClick() {
        generarTarjetas(numeroFuncionesDatosEIF, containerFuncionesDatosEIF, "Agregar usuario");
        numero_funciones_datosEIF = Integer.parseInt(numeroFuncionesDatosEIF.getText());
        //System.out.println("EIF: " + numero_funciones_datosILF);
    }

    //Acciones para agregar de las tarjetas de las funciones de las transacciones
    @FXML
    protected void onGenerarFuncionesTransaccionesEI(){
        generarTarjetas(numeroFuncionesTransaccionesEI, containerFuncionesTransaccionesEI, "Agregar Usuario");
    }
    @FXML
    protected void onGenerarFuncionesTransaccionesEO(){
        generarTarjetas(numeroFuncionesTransaccionesEO, containerFuncionesTransaccionesEO, "Agregar Usuario");
    }
    @FXML
    protected void onGenerarFuncionesTransaccionesEQ(){
        generarTarjetas(numeroFuncionesTransaccionesEQ, containerFuncionesTransaccionesEQ, "Agregar Usuario");
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

    private VBox crearTarjetaEditable(String sugerencia) {
        VBox tarjeta = new VBox(8);
        tarjeta.setPadding(new Insets(10));
        tarjeta.setStyle("-fx-border-color: #abb2b9; -fx-border-radius: 5; -fx-background-color: white;");

        // Campo para el nombre
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej: " + sugerencia);

        tarjeta.getChildren().addAll(new Label("Nombre del elemento:"), txtNombre);
        return tarjeta;
    }
}