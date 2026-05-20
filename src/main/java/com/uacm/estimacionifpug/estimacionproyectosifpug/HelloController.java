package com.uacm.estimacionifpug.estimacionproyectosifpug;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML private TextField numeroDeMesesDelTrabajo;
    @FXML private TextField numeroFuncionesDatosILF;
    @FXML private TextField numeroFuncionesDatosEIF;
    @FXML private TextField numeroFuncionesTransaccionesEI;
    @FXML private TextField numeroFuncionesTransaccionesEO;
    @FXML private TextField numeroFuncionesTransaccionesEQ;

    @FXML private VBox containerFuncionesDatosILF;
    @FXML private VBox containerFuncionesDatosEIF;
    @FXML private VBox containerFuncionesTransaccionesEI;
    @FXML private VBox containerFuncionesTransaccionesEO;
    @FXML private VBox containerFuncionesTransaccionesEQ;
    @FXML private VBox containerGSCs; // Contenedor FXML para las 14 GSCs

    @FXML private TextArea txtResumen; //Reporte final

    //Listas para registrar internamente los controles de cada tarjeta generada
    private final List<TarjetaDatosControles> listaILF = new ArrayList<>();
    private final List<TarjetaDatosControles> listaEIF = new ArrayList<>();
    private final List<TarjetaTransaccionControles> listaEI = new ArrayList<>();
    private final List<TarjetaTransaccionControles> listaEO = new ArrayList<>();
    private final List<TarjetaTransaccionControles> listaEQ = new ArrayList<>();

    private int totalPuntosFuncionSinAjuste; //Contador para los puntos de funcion sin ajustar
    public long totalPuntosFuncionAjustados; //COntador para los puntos de funcion con ajuste

    // Matriz con los títulos y las opciones exactas proporcionadas de las 14 GSC'S
    private final String[][] opcionesGSCs = {
            {"1. Comunicación de Datos", "0: App \"stand-alone\" sin red.", "1: Solo usa una impresora local.", "2: Descarga archivos vía FTP simple.", "3: Usa servicios Web (APIs) estándar.", "4: Integración fluida con múltiples sistemas remotos.", "5: Redes de alta velocidad dedicadas con protocolos específicos."},
            {"2. Procesamiento Distribuido", "0: Todo en un solo servidor/PC.", "1: El usuario prepara datos y el servidor los procesa luego.", "2: Los datos se capturan en un sitio y se procesan en otro.", "3: Procesamiento en varios servidores bajo el control de la app.", "4: Procesamiento dinámico entre cliente y servidor.", "5: Sistemas en la nube con balanceo de carga automático y global."},
            {"3. Rendimiento (Performance)", "0: No hay requisitos de tiempo.", "1: Requisitos de tiempo de respuesta normales.", "2: El tiempo es crítico solo en horas pico.", "3: Tiempos de respuesta definidos para todas las funciones (ej. < 2 seg).", "4: Requiere análisis de rendimiento en el diseño.", "5: Sistemas de tiempo real estricto (ej. telemedicina)."},
            {"4. Configuración del Equipamiento", "0: El hardware sobra para la app.", "1: Restricciones mínimas de memoria.", "2: Debe correr en laptops estándar de la empresa.", "3: Requiere procesadores o memoria específica.", "4: Limitaciones fuertes de hardware que afectan el código.", "5: Dispositivos embebidos con recursos extremadamente limitados."},
            {"5. Tasa de Transacciones", "0: 10 transacciones al día.", "1: Uso ocasional.", "2: Uso constante sin picos.", "3: El volumen requiere optimización de BD.", "4: Requiere servidores de transacciones dedicados.", "5: Millones de transacciones por minuto (ej. procesador de pagos)."},
            {"6. Entrada de Datos en Línea", "0: Todo entra por lotes (batch).", "1: 1% al 7% es interactivo.", "2: 8% al 15% es interactivo.", "3: 16% al 23% es interactivo.", "4: 24% al 30% es interactivo.", "5: > 30% de la app son pantallas de captura."},
            {"7. Eficiencia del Usuario Final", "0: Interfaz de consola (texto plano).", "1: Navegación básica entre pantallas.", "2: Uso de mouse y menús.", "3: Teclas de acceso rápido y autocompletado.", "4: Pantallas táctiles o interfaces gráficas avanzadas.", "5: Diseño UX para alta velocidad (ej. cajero de supermercado)."},
            {"8. Actualización en Línea", "0: Los archivos se actualizan por la noche (batch).", "1: Actualiza solo tablas de referencia.", "2: Pocos archivos maestros se actualizan en línea.", "3: La mayoría de los archivos clave son en línea.", "4: Protección contra pérdida de datos en actualizaciones.", "5: Actualización inmediata con réplica en tiempo real."},
            {"9. Procesamiento Complejo", "0: Solo mueve datos.", "1: Operaciones matemáticas simples.", "2: Validaciones lógicas simples.", "3: Muchos \"If-Then-Else\" y lógica de negocio extensa.", "4: Cálculos estadísticos o matemáticos pesados.", "5: Inteligencia Artificial o criptografía avanzada."},
            {"10. Reusabilidad", "0: Se usará solo en este proyecto.", "1: Código reutilizable dentro de este mismo sistema.", "2: Algunas funciones las usará otro sistema.", "3: Módulos diseñados para ser productos estándar.", "4: App diseñada como una librería para otros desarrolladores.", "5: Es un Framework base para toda la organización."},
            {"11. Facilidad de Instalación", "0: No hay requisitos de instalación.", "1: Instalación manual con guía.", "2: Instalador automático simple (setup.exe).", "3: Instalación y conversión de datos automática.", "4: Herramientas de migración de datos complejas incluidas.", "5: Despliegue automático en miles de nodos (Docker/K8s)."},
            {"12. Facilidad de Operación", "0: No requiere respaldo ni mantenimiento especial.", "1: Procesos manuales simples de respaldo.", "2: Alertas de error básicas.", "3: Recuperación automática ante fallas leves.", "4: Gestión de logs y monitoreo avanzado.", "5: Autogestión (Self-healing), no requiere operador."},
            {"13. Ubicaciones Múltiples", "0: Un solo usuario, un solo sitio.", "1: Varias PC en una misma oficina.", "2: Diferentes sucursales con mismo hardware.", "3: Diferentes países (requiere multi-idioma).", "4: Adaptación a leyes/impuestos de distintos países.", "5: Configuración dinámica por usuario según su ubicación."},
            {"14. Facilidad de Cambio", "0: Hard-coded (hay que programar para cambiar algo).", "1: El programador cambia un archivo de texto.", "2: Tablas de parámetros simples.", "3: Panel de configuración para el usuario.", "4: El usuario puede crear reportes personalizados.", "5: El usuario cambia la lógica mediante un motor de reglas visual."}
    };

    private final List<CheckBox[]> gruposDeCasillas = new ArrayList<>();

    //Clases auxiliares para empaquetar las referencias a los TextFields
    private static class TarjetaDatosControles {
        TextField name, det, ret;
        TarjetaDatosControles(TextField n, TextField d, TextField r) { name = n; det = d; ret = r; }
    }

    private static class TarjetaTransaccionControles {
        TextField name, det, ftr;
        TarjetaTransaccionControles(TextField n, TextField d, TextField f) { name = n; det = d; ftr = f; }
    }

    @FXML
    public void initialize() {
        // Construcción dinámica de la interfaz para las 14 GSCs
        for (String[] gsc : opcionesGSCs) {
            VBox bloqueGSC = new VBox(6);
            bloqueGSC.setPadding(new Insets(10));
            bloqueGSC.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dcdde1; -fx-border-radius: 5;");

            Label lblTitulo = new Label(gsc[0]);
            lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495e;");
            bloqueGSC.getChildren().add(lblTitulo);

            CheckBox[] casillasDeEsteGrupo = new CheckBox[6];

            // Añadir las opciones de la 0 a la 5
            for (int i = 1; i <= 6; i++) {
                final int indicePuntaje = i - 1;
                CheckBox chk = new CheckBox(gsc[i]);
                casillasDeEsteGrupo[indicePuntaje] = chk;

                // Lógica de exclusividad: si se marca una, se limpian las demás del mismo bloque
                chk.setOnAction(event -> {
                    if (chk.isSelected()) {
                        for (CheckBox c : casillasDeEsteGrupo) {
                            if (c != chk) c.setSelected(false);
                        }
                    }
                });

                bloqueGSC.getChildren().add(chk);
            }

            gruposDeCasillas.add(casillasDeEsteGrupo);
            containerGSCs.getChildren().add(bloqueGSC);
        }
    }

    // Quita los prefijos numéricos de los textos informativos para que la UI se vea limpia
    private String chkTextoLimpio(String textoOriginal, int indice) {
        return indice + ": " + textoOriginal;
    }

    @FXML
    protected void onGenerarFuncionesDatosILFClick() {
        containerFuncionesDatosILF.getChildren().clear();
        listaILF.clear();
        generarTarjetasDatos(numeroFuncionesDatosILF,
                containerFuncionesDatosILF, "Archivo Usuarios", listaILF);
    }

    @FXML
    protected void onGenerarFuncionesDatosEIFClick() {
        containerFuncionesDatosEIF.getChildren().clear();
        listaEIF.clear();
        generarTarjetasDatos(numeroFuncionesDatosEIF,
                containerFuncionesDatosEIF, "Archivo Externo", listaEIF);
    }

    @FXML
    protected void onGenerarFuncionesTransaccionesEI(){
        containerFuncionesTransaccionesEI.getChildren().clear();
        listaEI.clear();
        generarTarjetasTransacciones(numeroFuncionesTransaccionesEI,
                containerFuncionesTransaccionesEI, "Crear Usuario", listaEI);
    }

    @FXML
    protected void onGenerarFuncionesTransaccionesEO(){
        containerFuncionesTransaccionesEO.getChildren().clear();
        listaEO.clear();
        generarTarjetasTransacciones(numeroFuncionesTransaccionesEO,
                containerFuncionesTransaccionesEO, "Reporte Usuarios", listaEO);
    }

    @FXML
    protected void onGenerarFuncionesTransaccionesEQ(){
        containerFuncionesTransaccionesEQ.getChildren().clear();
        listaEQ.clear();
        generarTarjetasTransacciones(numeroFuncionesTransaccionesEQ, containerFuncionesTransaccionesEQ, "Buscar Usuario", listaEQ);
    }



    private void generarTarjetasDatos(TextField input, VBox container, String sugerencia,
                                      List<TarjetaDatosControles> listaMemoria) {
        try {
            int cantidad = Integer.parseInt(input.getText());
            for (int i = 1; i <= cantidad; i++) {
                container.getChildren().add(crearTarjetaDatos(sugerencia + " "
                        + i, listaMemoria));
            }
        } catch (NumberFormatException e) {
            txtResumen.setText("Error: Ingresa un número válido de tarjetas.");
        }
    }

    private void generarTarjetasTransacciones(TextField input, VBox container, String sugerencia,
                                              List<TarjetaTransaccionControles> listaMemoria) {
        try {
            int cantidad = Integer.parseInt(input.getText());
            for (int i = 1; i <= cantidad; i++) {
                container.getChildren().add(crearTarjetaTransaccion(sugerencia + " " + i, listaMemoria));
            }
        } catch (NumberFormatException e) {
            txtResumen.setText("Error: Ingresa un número válido de tarjetas.");
        }
    }

    private VBox crearTarjetaDatos(String sugerencia, List<TarjetaDatosControles> listaMemoria) {
        VBox tarjeta = new VBox(4);
        tarjeta.setPadding(new Insets(10));
        tarjeta.setStyle("-fx-border-color: #abb2b9; -fx-border-radius: 5; -fx-background-color: white;");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej: " + sugerencia);
        TextField txtDET = new TextField();
        txtDET.setPromptText("Ej: id, nombre, correo");
        TextField txtRET = new TextField();
        txtRET.setPromptText("Ej: Grupo_A, Grupo_B (ó vacío)");

        listaMemoria.add(new TarjetaDatosControles(txtNombre, txtDET, txtRET));

        tarjeta.getChildren().addAll(
                new Label("Nombre de la Función de Datos:"), txtNombre,
                new Label("DET (Campos por comas):"), txtDET,
                new Label("RET (Subgrupos por comas):"), txtRET
        );
        return tarjeta;
    }

    private VBox crearTarjetaTransaccion(String sugerencia, List<TarjetaTransaccionControles> listaMemoria) {
        VBox tarjeta = new VBox(4);
        tarjeta.setPadding(new Insets(10));
        tarjeta.setStyle("-fx-border-color: #3F7EA6; -fx-border-radius: 5; -fx-background-color: white;");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej: " + sugerencia);
        TextField txtDET = new TextField();
        txtDET.setPromptText("Ej: input_nombre, btn_guardar");
        TextField txtFTR = new TextField();
        txtFTR.setPromptText("Ej: Tabla_Usuarios, Tabla_Logins");

        listaMemoria.add(new TarjetaTransaccionControles(txtNombre, txtDET, txtFTR));

        tarjeta.getChildren().addAll(
                new Label("Nombre de la Transacción:"), txtNombre,
                new Label("DET (Elementos por comas):"), txtDET,
                new Label("FTR (Archivos referenciados por comas):"), txtFTR
        );
        return tarjeta;
    }

    // BOTÓN DE CÁLCULO PRINCIPAL
    @FXML
    protected void onCalcularPuntosFuncionClick() {
        String complejidadSistema = "";
        long productividad = 0;
        long esfuerzo = 0;
        double numPersonas = 0;
        StringBuilder reporte = new StringBuilder();
        reporte.append("=========================================================\n");
        reporte.append("          REPORTE DE CONTEO ELEMENTOS IFPUG              \n");
        reporte.append("=========================================================\n\n");

        //Procesar ILF
        reporte.append("--- FUNCIONES DE DATOS: ILF ---\n");
        procesarListaDatosILF(listaILF, reporte);

        //Procesar EIF
        reporte.append("\n--- FUNCIONES DE DATOS: EIF ---\n");
        procesarListaDatosEIF(listaEIF, reporte);

        //Procesar EI
        reporte.append("\n--- FUNCIONES DE TRANSACCIÓN: EI ---\n");
        procesarListaTransaccionesEntradaExterna(listaEI, reporte);

        //Procesar EO
        reporte.append("\n--- FUNCIONES DE TRANSACCIÓN: EO ---\n");
        procesarListaTransaccionesEO(listaEO, reporte);

        //Procesar EQ
        reporte.append("\n--- FUNCIONES DE TRANSACCIÓN: EQ ---\n");
        procesarListaTransaccionesEQ(listaEQ, reporte);

        //Total de puntos de funcion
        reporte.append("\n---TOTAL DE PUNTOS DE FUNCION SIN AJUSTAR----\n");
        reporte.append("PF =").append(totalPuntosFuncionSinAjuste);

        //Cálculo de las 14 caractericas GSC'S
        reporte.append("\n=========================================================");
        reporte.append("                 FACTOR DE AJUSTE (VAF)                  \n");
        reporte.append("=========================================================\n\n");

        int sumaGSCs = 0;
        reporte.append("--- DESGLOSE DEL FACTOR DE AJUSTE (VAF) ---\n");

        // Recorremos los 14 grupos para recopilar de forma interna el valor marcado
        for (int i = 0; i < gruposDeCasillas.size(); i++) {
            CheckBox[] grupo = gruposDeCasillas.get(i);
            String nombreGSC = opcionesGSCs[i][0];
            int puntajeDeEstaGSC = 0;

            // Evaluamos cuál casilla del 0 al 5 fue seleccionada
            for (int puntaje = 0; puntaje <= 5; puntaje++) {
                if (grupo[puntaje].isSelected()) {
                    puntajeDeEstaGSC = puntaje;
                    break;
                }
            }

            sumaGSCs += puntajeDeEstaGSC;
            reporte.append(String.format("  > %-36s : %d pts\n", nombreGSC, puntajeDeEstaGSC));
        }

        // Aplicación de la fórmula estándar de la norma IFPUG
        double vaf = 0.65 + (0.01 * sumaGSCs);

        // Verificar la complejidad del sistema según el impacto del Factor VAF
        if (vaf < 0.85) {
            complejidadSistema = "Sistema muy simple";
        } else if (vaf >= 0.85 && vaf <= 1.15) {
            complejidadSistema = "Sistema promedio";
        } else {
            complejidadSistema = "Sistema altamente complejo";
        }

        //Total de puntos de funcion con ajuste
        totalPuntosFuncionAjustados = Math.round(totalPuntosFuncionSinAjuste * vaf);

        reporte.append("\n---------------------------------------------------------\n");
        reporte.append(String.format("Suma Total del Grado de Influencia (GSCs): %d\n", sumaGSCs));
        reporte.append(String.format("Factor de Ajuste de Valor Calculado (VAF): %.2f\n", vaf));
        reporte.append(String.format("Complejidad del sistema: %s\n", complejidadSistema));
        reporte.append(String.format("Puntos de función con ajuste PF = %d\n", totalPuntosFuncionAjustados));


        reporte.append("\n=========================================================\n");
        reporte.append(" ESTIMACIONES DE PRODUCTIVIDAD, ESFUERZO, COSTO Y TIEMPO  \n");
        reporte.append("=========================================================\n\n");

        // Prevenir colapso por división entre cero si no hay puntos de función calculados
        if (totalPuntosFuncionAjustados > 0) {
            productividad = 8 / totalPuntosFuncionAjustados;
            esfuerzo = totalPuntosFuncionAjustados * productividad;
            numPersonas = (double) esfuerzo / (Integer.parseInt(numeroDeMesesDelTrabajo.getText().trim()) * 160);

            reporte.append(String.format("Productividad en 8 horas laborales: %d horas/AFP\n", productividad));
            reporte.append(String.format("Esfuerzo: %d horas\n", esfuerzo));
            reporte.append(String.format("------> Trabajo total: %d\n", esfuerzo));
            reporte.append(String.format("Número de trabajadores necesarios: %.2f\n", numPersonas));
        } else {
            reporte.append("No se puede calcular productividad ni esfuerzo: Los Puntos de Función Ajustados deben ser mayores a 0.\n");
        }

        txtResumen.setText(reporte.toString());
    }

    //Metodo que calcula los DET'S Y RET'S de la Lista de datos ILF
    private void procesarListaDatosILF(List<TarjetaDatosControles> lista, StringBuilder sb) {
        String complejidad = "";
        if(lista.isEmpty()) { sb.append("  (No se registraron elementos)\n"); return; }

        for (TarjetaDatosControles t : lista) {
            String nombre = t.name.getText().trim().isEmpty() ? "Sin Nombre" : t.name.getText().trim();

            // Lógica contar DETs
            int dets = t.det.getText().trim().isEmpty() ? 0 : t.det.getText().split(",").length;

            // Lógica contar RETs (Regla IFPUG: mínimo 1)
            int rets = t.ret.getText().trim().isEmpty() ? 1 : t.ret.getText().split(",").length;


            //Tabla para medir la compelejidad de Archivos Lógicos Internos (ALI) y Archivos de Interfase Externa (AIE)
            //La complejidad se basa en el número de Tipos de Datos Elementales (DET) Tipos de Registros
            //Lógicos (RET).
            if(dets >= 1 && dets <= 19){
                if(rets == 1){
                    complejidad = Complejidad.BAJO.name();
                }else{
                    if(rets >= 2 && rets <= 5){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if (rets >= 6){
                            complejidad = Complejidad.MEDIO.name();
                        }
                    }
                }
            }else{
                if(dets >= 20 && dets <= 50){
                    if(rets == 1){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if(rets >= 2 && rets <= 5){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if (rets >= 6){
                                complejidad = Complejidad.ALTO.name();
                            }
                        }
                    }
                }else{
                    if(dets >= 51){
                        if(rets == 1){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if(rets >= 2 && rets <= 5){
                                complejidad = Complejidad.ALTO.name();
                            }else{
                                if (rets >= 6){
                                    complejidad = Complejidad.ALTO.name();
                                }
                            }
                        }
                    }
                }
            }

            if(complejidad.equals(Complejidad.BAJO.name())){
                totalPuntosFuncionSinAjuste += 7;
            }else{
                if(complejidad.equals(Complejidad.MEDIO.name())){
                    totalPuntosFuncionSinAjuste += 10;
                }else {
                    if(complejidad.equals(Complejidad.ALTO.name())){
                        totalPuntosFuncionSinAjuste += 15;
                    }
                }
            }

            sb.append(String.format("  > [%s] -> DETs: %d | RETs: %d\n Complejidad: %s\n", nombre, dets, rets, complejidad));
        }
    }


    private void procesarListaDatosEIF(List<TarjetaDatosControles> lista, StringBuilder sb) {
        String complejidad = "";
        if(lista.isEmpty()) { sb.append("  (No se registraron elementos)\n"); return; }

        for (TarjetaDatosControles t : lista) {
            String nombre = t.name.getText().trim().isEmpty() ? "Sin Nombre" : t.name.getText().trim();

            // Lógica contar DETs
            int dets = t.det.getText().trim().isEmpty() ? 0 : t.det.getText().split(",").length;

            // Lógica contar RETs (Regla IFPUG: mínimo 1)
            int rets = t.ret.getText().trim().isEmpty() ? 1 : t.ret.getText().split(",").length;

            //Tabla para medir la compelejidad de Archivos Lógicos Internos (ALI) y Archivos de Interfase Externa (AIE)
            //La complejidad se basa en el número de Tipos de Datos Elementales (DET) Tipos de Registros
            //Lógicos (RET).
            if(dets >= 1 && dets <= 19){
                if(rets == 1){
                    complejidad = Complejidad.BAJO.name();
                }else{
                    if(rets >= 2 && rets <= 5){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if (rets >= 6){
                            complejidad = Complejidad.MEDIO.name();
                        }
                    }
                }
            }else{
                if(dets >= 20 && dets <= 50){
                    if(rets == 1){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if(rets >= 2 && rets <= 5){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if (rets >= 6){
                                complejidad = Complejidad.ALTO.name();
                            }
                        }
                    }
                }else{
                    if(dets >= 51){
                        if(rets == 1){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if(rets >= 2 && rets <= 5){
                                complejidad = Complejidad.ALTO.name();
                            }else{
                                if (rets >= 6){
                                    complejidad = Complejidad.ALTO.name();
                                }
                            }
                        }
                    }
                }
            }

            if(complejidad.equals(Complejidad.BAJO.name())){
                totalPuntosFuncionSinAjuste += 5;
            }else{
                if(complejidad.equals(Complejidad.MEDIO.name())){
                    totalPuntosFuncionSinAjuste += 7;
                }else {
                    if(complejidad.equals(Complejidad.ALTO.name())){
                        totalPuntosFuncionSinAjuste += 10;
                    }
                }
            }

            sb.append(String.format("  > [%s] -> DETs: %d | RETs: %d\n Complejidad: %s\n", nombre, dets, rets, complejidad));
        }
    }

    private void procesarListaTransaccionesEntradaExterna(List<TarjetaTransaccionControles> lista, StringBuilder sb) {
        String complejidad = "";
        if(lista.isEmpty()) { sb.append("  (No se registraron elementos)\n"); return; }

        for (TarjetaTransaccionControles t : lista) {
            String nombre = t.name.getText().trim().isEmpty() ? "Sin Nombre" : t.name.getText().trim();

            // Lógica contar DETs
            int dets = t.det.getText().trim().isEmpty() ? 0 : t.det.getText().split(",").length;

            // Lógica contar FTRs (Regla IFPUG: mínimo 1)
            int ftrs = t.ftr.getText().trim().isEmpty() ? 1 : t.ftr.getText().split(",").length;

            //Tabla para medir la compelejidad de Entradas Externas (EE)
            //La complejidad se basa en el número de Tipos de Datos Elementales (DET) y Archivos Lógicos Referenciados (FTR).
            if(dets >= 1 && dets <= 4){
                if(ftrs >= 0 && ftrs <= 1){
                    complejidad = Complejidad.BAJO.name();
                }else{
                    if(ftrs == 2){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if (ftrs >= 3){
                            complejidad = Complejidad.MEDIO.name();
                        }
                    }
                }
            }else{
                if(dets >= 5 && dets <= 15){
                    if(ftrs >= 0 && ftrs <= 1){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if(ftrs == 2){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if (ftrs >= 3){
                                complejidad = Complejidad.ALTO.name();
                            }
                        }
                    }
                }else{
                    if(dets >= 16){
                        if(ftrs >= 0 && ftrs <= 1){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if(ftrs == 2){
                                complejidad = Complejidad.ALTO.name();
                            }else{
                                if (ftrs >= 3){
                                    complejidad = Complejidad.ALTO.name();
                                }
                            }
                        }
                    }
                }
            }

            //Tabla para sumar los puntos de funcion de las entradas externas
            if(complejidad.equals(Complejidad.BAJO.name())){
                totalPuntosFuncionSinAjuste += 3;
            }else{
                if(complejidad.equals(Complejidad.MEDIO.name())){
                    totalPuntosFuncionSinAjuste += 4;
                }else {
                    if(complejidad.equals(Complejidad.ALTO.name())){
                        totalPuntosFuncionSinAjuste += 6;
                    }
                }
            }

            sb.append(String.format("  > [%s] -> DETs: %d | FTRs: %d\n Complejidad: %s\n", nombre, dets, ftrs, complejidad));
        }
    }

    private void procesarListaTransaccionesEO(List<TarjetaTransaccionControles> lista, StringBuilder sb) {
        String complejidad = "";
        if(lista.isEmpty()) { sb.append("  (No se registraron elementos)\n"); return; }

        for (TarjetaTransaccionControles t : lista) {
            String nombre = t.name.getText().trim().isEmpty() ? "Sin Nombre" : t.name.getText().trim();

            // Lógica contar DETs
            int dets = t.det.getText().trim().isEmpty() ? 0 : t.det.getText().split(",").length;

            // Lógica contar FTRs (Regla IFPUG: mínimo 1)
            int ftrs = t.ftr.getText().trim().isEmpty() ? 1 : t.ftr.getText().split(",").length;

            //Tabla para la complejidad de Salidas Externas (SE) y Consultas Externas (CE)
            //La complejidad se basa en el número de Tipos de Datos Elementales (DET) y Archivos Lógicos Referenciados (FTR).
            if(dets >= 1 && dets <= 5){
                if(ftrs >= 0 && ftrs <= 1){
                    complejidad = Complejidad.BAJO.name();
                }else {
                    if(ftrs >= 2 && ftrs <= 3){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if(ftrs >= 4){
                            complejidad = Complejidad.MEDIO.name();
                        }
                    }
                }
            }else{
                if(dets >= 6 && dets <= 19){
                    if(ftrs >= 0 && ftrs <= 1){
                        complejidad = Complejidad.BAJO.name();
                    }else {
                        if(ftrs >= 2 && ftrs <= 3){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if(ftrs >= 4){
                                complejidad = Complejidad.ALTO.name();
                            }
                        }
                    }
                }else{
                    if (dets >= 20){
                        if(ftrs >= 0 && ftrs <= 1){
                            complejidad = Complejidad.MEDIO.name();
                        }else {
                            if(ftrs >= 2 && ftrs <= 3){
                                complejidad = Complejidad.ALTO.name();
                            }else{
                                if(ftrs >= 4){
                                    complejidad = Complejidad.ALTO.name();
                                }
                            }
                        }
                    }
                }
            }

            if(complejidad.equals(Complejidad.BAJO.name())){
                totalPuntosFuncionSinAjuste += 4;
            }else{
                if(complejidad.equals(Complejidad.MEDIO.name())){
                    totalPuntosFuncionSinAjuste += 5;
                }else {
                    if(complejidad.equals(Complejidad.ALTO.name())){
                        totalPuntosFuncionSinAjuste += 7;
                    }
                }
            }

            sb.append(String.format("  > [%s] -> DETs: %d | FTRs: %d\n Complejidad: %s\n", nombre, dets, ftrs, complejidad));
        }
    }

    private void procesarListaTransaccionesEQ(List<TarjetaTransaccionControles> lista, StringBuilder sb) {
        String complejidad = "";
        if(lista.isEmpty()) { sb.append("  (No se registraron elementos)\n"); return; }

        for (TarjetaTransaccionControles t : lista) {
            String nombre = t.name.getText().trim().isEmpty() ? "Sin Nombre" : t.name.getText().trim();

            // Lógica contar DETs
            int dets = t.det.getText().trim().isEmpty() ? 0 : t.det.getText().split(",").length;

            // Lógica contar FTRs (Regla IFPUG: mínimo 1)
            int ftrs = t.ftr.getText().trim().isEmpty() ? 1 : t.ftr.getText().split(",").length;

            //Tabla para la complejidad de Salidas Externas (SE) y Consultas Externas (CE)
            //La complejidad se basa en el número de Tipos de Datos Elementales (DET) y Archivos Lógicos Referenciados (FTR).
            if(dets >= 1 && dets <= 5){
                if(ftrs >= 0 && ftrs <= 1){
                    complejidad = Complejidad.BAJO.name();
                }else {
                    if(ftrs >= 2 && ftrs <= 3){
                        complejidad = Complejidad.BAJO.name();
                    }else{
                        if(ftrs >= 4){
                            complejidad = Complejidad.MEDIO.name();
                        }
                    }
                }
            }else{
                if(dets >= 6 && dets <= 19){
                    if(ftrs >= 0 && ftrs <= 1){
                        complejidad = Complejidad.BAJO.name();
                    }else {
                        if(ftrs >= 2 && ftrs <= 3){
                            complejidad = Complejidad.MEDIO.name();
                        }else{
                            if(ftrs >= 4){
                                complejidad = Complejidad.ALTO.name();
                            }
                        }
                    }
                }else{
                    if (dets >= 20){
                        if(ftrs >= 0 && ftrs <= 1){
                            complejidad = Complejidad.MEDIO.name();
                        }else {
                            if(ftrs >= 2 && ftrs <= 3){
                                complejidad = Complejidad.ALTO.name();
                            }else{
                                if(ftrs >= 4){
                                    complejidad = Complejidad.ALTO.name();
                                }
                            }
                        }
                    }
                }
            }

            if(complejidad.equals(Complejidad.BAJO.name())){
                totalPuntosFuncionSinAjuste += 3;
            }else{
                if(complejidad.equals(Complejidad.MEDIO.name())){
                    totalPuntosFuncionSinAjuste += 4;
                }else {
                    if(complejidad.equals(Complejidad.ALTO.name())){
                        totalPuntosFuncionSinAjuste += 6    ;
                    }
                }
            }

            sb.append(String.format("  > [%s] -> DETs: %d | FTRs: %d\n Complejidad: %s\n", nombre, dets, ftrs, complejidad));
        }
    }
}