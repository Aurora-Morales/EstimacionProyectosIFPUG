package com.uacm.estimacionifpug.estimacionproyectosifpug;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML private TextField numeroDeMesesDelTrabajo;
    @FXML private TextField numeroDePersonasEnEquipo;
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

    private int totalPuntosFuncionSinAjuste; //Contador para los puntos de función sin ajustar
    public long totalPuntosFuncionAjustados; //Contador para los puntos de función con ajuste

    //Cada punto de función requiere 10 horas de trabajo
    //Este valor no es universal, depende de:
    //    -experiencia del equipo
    //    -tecnología
    //    -complejidad real
    long productividad = 10; // Tasa fija base de horas por punto
    double costo_hora = 156; // Sueldo de un desarrollador en java + spring
    double eficiencia = 0.7;

    // Matriz con los títulos y las opciones exactas proporcionadas de las 14 GSCS
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
        // 1. REINICIAR CONTADORES GLOBALES (Evita que se acumulen los valores al recalcular)
        totalPuntosFuncionSinAjuste = 0;
        totalPuntosFuncionAjustados = 0;

        String complejidadSistema = "";
        long esfuerzo = 0;
        double numeroPersonasEstimado;
        double tiempoAjuste = 0.0;
        double tiempo = 0.0;
        double costo = 0.0;
        int loc = 0;
        double numeroPersonas;
        double numeroDeMeses;
        double velocidad = 0;

        //VAlidar si no se ingresan numeros
        try {
            numeroPersonas = Integer.parseInt(numeroDePersonasEnEquipo.getText().trim());
            numeroDeMeses = Double.parseDouble(numeroDeMesesDelTrabajo.getText().trim());
        } catch (NumberFormatException e) {
            txtResumen.setText("Error: Por favor introduce valores numéricos válidos en 'Número de personas' y 'Meses de trabajo'.");
            return;
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("=========================================================\n");
        reporte.append("          REPORTE DE CONTEO ELEMENTOS IFPUG              \n");
        reporte.append("=========================================================\n\n");

        // Procesar listas (ahora sumarán desde 0)
        reporte.append("--- FUNCIONES DE DATOS: ILF ---\n");
        procesarListaDatosILF(listaILF, reporte);

        reporte.append("\n--- FUNCIONES DE DATOS: EIF ---\n");
        procesarListaDatosEIF(listaEIF, reporte);

        reporte.append("\n--- FUNCIONES DE TRANSACCIÓN: EI ---\n");
        procesarListaTransaccionesEntradaExterna(listaEI, reporte);

        reporte.append("\n--- FUNCIONES DE TRANSACCIÓN: EO ---\n");
        procesarListaTransaccionesEO(listaEO, reporte);

        reporte.append("\n--- FUNCIONES DE TRANSACCIÓN: EQ ---\n");
        procesarListaTransaccionesEQ(listaEQ, reporte);

        //Total de puntos de funcion
        reporte.append("\n---TOTAL DE PUNTOS DE FUNCION SIN AJUSTAR----\n");
        reporte.append("PF =").append(totalPuntosFuncionSinAjuste);

        //Cálculo de las 14 caractericas GSC'S
        reporte.append("\n=========================================================\n");
        reporte.append("                 FACTOR DE AJUSTE (VAF)                  \n");
        reporte.append("=========================================================\n\n");

        int sumaGSCs = 0;
        reporte.append("--- DESGLOSE DEL FACTOR DE AJUSTE (VAF) ---\n");

        // Recorremos los 14 grupos para recopilar de forma interna el valor marcado
        for (int i = 0; i < gruposDeCasillas.size(); i++) {
            CheckBox[] grupo = gruposDeCasillas.get(i);
            String nombreGSC = opcionesGSCs[i][0];
            int puntajeDeEstaGSC = 0;

            // Evaluar casilla del 0 al 5 que fue seleccionada
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

        if (vaf < 0.65) {
            complejidadSistema = "Sistema muy simple";
        } else if (vaf >= 0.65 && vaf <= 1.15) {
            complejidadSistema = "Sistema promedio";
        } else {
            complejidadSistema = "Sistema altamente complejo";
        }

        totalPuntosFuncionAjustados = Math.round(totalPuntosFuncionSinAjuste * vaf);

        reporte.append("\n---------------------------------------------------------\n");
        reporte.append(String.format("Suma Total del Grado de Influencia (GSCs): %d\n", sumaGSCs));
        reporte.append(String.format("Factor de Ajuste de Valor Calculado (VAF): %.2f\n", vaf));
        reporte.append(String.format("Complejidad del sistema: %s de influencia\n", complejidadSistema));
        reporte.append(String.format("Puntos de función con ajuste APF = %d\n", totalPuntosFuncionAjustados));

        reporte.append("\n=========================================================\n");
        reporte.append(" ESTIMACIONES DE PRODUCTIVIDAD, ESFUERZO, COSTO Y TIEMPO  \n");
        reporte.append("=========================================================\n\n");

        // Prevenir colapso por división entre cero si no hay puntos de función calculados
        if (totalPuntosFuncionSinAjuste > 0) {
            esfuerzo = totalPuntosFuncionAjustados * productividad;
            costo = (double)esfuerzo * costo_hora;
            tiempoAjuste = ((double)esfuerzo/(numeroPersonas*160*eficiencia));

            // (Tus appends de reporte se quedan exactamente igual)
            reporte.append(String.format("Productividad de: %d horas/AFP por experiencia, tecnologia y complejidad real\n", productividad));
            reporte.append(String.format("Esfuerzo: %d horas\n", esfuerzo));

            //En caso de que no conocer el tiempo que necesita el proyecto en meses
            if(numeroPersonas == 0.0){
                numeroPersonasEstimado = (double) esfuerzo / (numeroDeMeses * 160);
                reporte.append(String.format("En %.2f meses se necesita de %.2f personas\n", numeroDeMeses, numeroPersonasEstimado));
            }else{
                //En caso de no conocer el número de personas necesarias para el tiempo definido en meses
                if(numeroDeMeses == 0.0){
                    tiempo = (double) esfuerzo / (numeroPersonas * 160);
                    reporte.append(String.format("Para %.2f personas el tiempo estimado para terminar el proyecto es en %.2f meses\n", numeroPersonas, tiempo));
                }else{
                    reporte.append("Es necesario conocer el tiempo o numero de personas necesarios para el proyecto\n");
                }
            }
            reporte.append(String.format("Tiempo estimado para terminar el producto con ajuste realista del 0.7 de eficiencia %.2f\n", tiempoAjuste));
            reporte.append(String.format("Costo del proyecto para un programador Junior que gana $81.87/h: $%.2f pesos mexicanos\n", costo));

        } else {
            reporte.append("No se puede calcular productividad ni esfuerzo: Los Puntos de Función Sin Ajustar deben ser mayores a 0.\n");
        }

        reporte.append("\n=========================================================\n");
        reporte.append("Recomendación de quipo por Puntos de función con ajuste (APF)\n");
        recomendacionEquipo(totalPuntosFuncionAjustados, reporte);
        reporte.append("\n=========================================================\n");

        reporte.append("\n=========================================================\n");
        reporte.append("         ESTIMACIONES POR FASES DEL CICLO DE VIDA          \n");
        reporte.append("=========================================================\n\n");

        estimacionxFasesDelCicloDeVida(esfuerzo, costo, tiempoAjuste, reporte);

        reporte.append("\n=========================================================\n");
        velocidad = totalPuntosFuncionSinAjuste/tiempo;
        reporte.append(String.format("Velocidad de entrega: %.2f PF/mes\n", velocidad));
        reporte.append("\n=========================================================\n");


        reporte.append("\n=========================================================\n");
        reporte.append("               ESTIMACION DEL TAMAÑO DEL CODIGO            \n");
        reporte.append("=========================================================\n\n");
        loc = 45*totalPuntosFuncionSinAjuste;
        reporte.append(String.format("Lineas de código necesarias para el proyecto: %d\n", loc));

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

    //Metodo que calcula los DET'S Y RET'S de la Lista de datos EIF
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

    //Metodo para la estimacion de las faces del ciclo de vida
    private void estimacionxFasesDelCicloDeVida(long esfuerzo, double costo, double tiempoAjuste, StringBuilder sb) {
        // Definición de los porcentajes del ciclo de vida en formato decimal (Double) proporcionados por el usuario
        double pctPlanificacion = 0.09;
        double pctEspecificacion = 0.11;
        double pctDiseno         = 0.15;
        double pctCodificacion   = 0.43;
        double pctPruebas        = 0.16;
        double pctImplantacion   = 0.06;

        // 1. Distribución del Esfuerzo (Horas)
        double esfPlan = esfuerzo * pctPlanificacion;
        double esfEsp  = esfuerzo * pctEspecificacion;
        double esfDis  = esfuerzo * pctDiseno;
        double esfCod  = esfuerzo * pctCodificacion;
        double esfPru  = esfuerzo * pctPruebas;
        double esfImp  = esfuerzo * pctImplantacion;

        // 2. Distribución de los Costos ($)
        double cosPlan = costo * pctPlanificacion;
        double cosEsp  = costo * pctEspecificacion;
        double cosDis  = costo * pctDiseno;
        double cosCod  = costo * pctCodificacion;
        double cosPru  = costo * pctPruebas;
        double cosImp  = costo * pctImplantacion;

        // 3. Distribución del Tiempo (Meses)
        double tiePlan = tiempoAjuste * pctPlanificacion;
        double tieEsp  = tiempoAjuste * pctEspecificacion;
        double tieDis  = tiempoAjuste * pctDiseno;
        double tieCod  = tiempoAjuste * pctCodificacion;
        double tiePru  = tiempoAjuste * pctPruebas;
        double tieImp  = tiempoAjuste * pctImplantacion;

        // Construcción del Reporte formateado en texto plano para el TextArea
        sb.append(String.format("%-18s | %-15s | %-15s | %-15s\n", "Fase del Ciclo", "Esfuerzo (hrs)", "Costo ($)", "Tiempo (meses)"));
        sb.append("-------------------------------------------------------------------------\n");
        sb.append(String.format("%-18s | %-15.2f | %-15.2f | %-15.2f\n", "1. Planificación", esfPlan, cosPlan, tiePlan));
        sb.append(String.format("%-18s | %-15.2f | %-15.2f | %-15.2f\n", "2. Especificación", esfEsp, cosEsp, tieEsp));
        sb.append(String.format("%-18s | %-15.2f | %-15.2f | %-15.2f\n", "3. Análisis/Diseño", esfDis, cosDis, tieDis));
        sb.append(String.format("%-18s | %-15.2f | %-15.2f | %-15.2f\n", "4. Codificación", esfCod, cosCod, tieCod));
        sb.append(String.format("%-18s | %-15.2f | %-15.2f | %-15.2f\n", "5. Pruebas", esfPru, cosPru, tiePru));
        sb.append(String.format("%-18s | %-15.2f | %-15.2f | %-15.2f\n", "6. Implantación", esfImp, cosImp, tieImp));
        sb.append("-------------------------------------------------------------------------\n");
        sb.append(String.format("%-18s | %-15d | %-15.2f | %-15.2f\n", "TOTAL ESTIMADO", esfuerzo, costo, tiempoAjuste));
    }

    //Metodo para verificar si los empleado necesarios para los puntos de función calculados
    private void recomendacionEquipo(long apf ,StringBuilder sb){
        String personarNecesarias = "";
        if(apf <= 0){
            personarNecesarias = "Error: No pueden haber puntos de funcion negativos o iguales a 0";
        }
        if(apf < 50){
            personarNecesarias = "1-3 personas";
        }else {
            if (apf <= 200){
                personarNecesarias = "3-7 personas";
            }else{
                personarNecesarias = "7+ personas";
            }
        }
        sb.append(personarNecesarias);
    }

    //Metodo para vaciar todos los campos llenos
    @FXML
    protected void onLimpiarCamposClick() {
        // 1. Limpiar los TextFields principales de entrada de texto
        numeroDeMesesDelTrabajo.clear();
        numeroDePersonasEnEquipo.clear();
        numeroFuncionesDatosILF.clear();
        numeroFuncionesDatosEIF.clear();
        numeroFuncionesTransaccionesEI.clear();
        numeroFuncionesTransaccionesEO.clear();
        numeroFuncionesTransaccionesEQ.clear();

        // 2. Limpiar las listas en memoria
        listaILF.clear();
        listaEIF.clear();
        listaEI.clear();
        listaEO.clear();
        listaEQ.clear();

        // 3. Limpiar los contenedores visuales de las tarjetas de la interfaz
        containerFuncionesDatosILF.getChildren().clear();
        containerFuncionesDatosEIF.getChildren().clear();
        containerFuncionesTransaccionesEI.getChildren().clear();
        containerFuncionesTransaccionesEO.getChildren().clear();
        containerFuncionesTransaccionesEQ.getChildren().clear();

        // 4. Desmarcar todos los CheckBox de las 14 GSCs
        for (CheckBox[] grupo : gruposDeCasillas) {
            for (CheckBox chk : grupo) {
                chk.setSelected(false);
            }
        }

        // 5. Reiniciar los contadores de puntos de función
        totalPuntosFuncionSinAjuste = 0;
        totalPuntosFuncionAjustados = 0;

        // 6. Limpiar el TextArea del reporte escrito
        txtResumen.clear();
    }

    @FXML
        protected void onPrecargarDatosVeterinariaClick() {
            // 1. Limpiar cualquier estado anterior para asegurar una carga limpia
            onLimpiarCamposClick();

            // 2. Definir parámetros de trabajo estimados por defecto
            numeroDePersonasEnEquipo.setText("1");
            numeroDeMesesDelTrabajo.setText("0");

            // 3. Asignar las cantidades exactas de funciones del PDF
            numeroFuncionesDatosILF.setText("4");
            numeroFuncionesDatosEIF.setText("0");
            numeroFuncionesTransaccionesEI.setText("6");
            numeroFuncionesTransaccionesEO.setText("3");
            numeroFuncionesTransaccionesEQ.setText("5");

            // 4. Forzar la generación visual de las tarjetas en la interfaz
            onGenerarFuncionesDatosILFClick();
            onGenerarFuncionesDatosEIFClick();
            onGenerarFuncionesTransaccionesEI();
            onGenerarFuncionesTransaccionesEO();
            onGenerarFuncionesTransaccionesEQ();

            // =========================================================================
            // 5. RELLENAR VALORES DE FUNCIONES DE DATOS (ILF)
            // =========================================================================
            // ILF 1: Gestión de Productos (9 DETs, 1 RET)
            if (listaILF.size() >= 1) {
                listaILF.get(0).name.setText("Gestión de Productos");
                listaILF.get(0).det.setText("id,nombre,codigo,categoria,precio,stock,stockMinimo,proveedor,fechaCaducidad");
                listaILF.get(0).ret.setText("Producto");
            }
            // ILF 2: Gestión de Usuarios (3 DETs, 1 RET)
            if (listaILF.size() >= 2) {
                listaILF.get(1).name.setText("Gestión de Usuarios");
                listaILF.get(1).det.setText("idUsuario,username,password");
                listaILF.get(1).ret.setText("Usuario");
            }
            // ILF 3: Registro de Pedidos (4 DETs, 1 RET)
            if (listaILF.size() >= 3) {
                listaILF.get(2).name.setText("Registro de Pedidos");
                listaILF.get(2).det.setText("idPedido,fechaEnvio,correo,costo");
                listaILF.get(2).ret.setText("Pedido");
            }
            // ILF 4: Registro de Pacientes Clínicos (9 DETs, 1 RET)
            if (listaILF.size() >= 4) {
                listaILF.get(3).name.setText("Registro de Pacientes Clínicos");
                listaILF.get(3).det.setText("idPaciente,nombreMascota,raza,edad,nombreDueno,telefono,descripcion, medicamentos,cajas");
                listaILF.get(3).ret.setText("Paciente");
            }

            // =========================================================================
            // 6. RELLENAR VALORES DE ENTRADAS EXTERNAS (EI) - Formulario/Escritura
            // =========================================================================
            // EI 1: Registrar Producto (8 DETs, 1 FTR)
            if (listaEI.size() >= 1) {
                listaEI.get(0).name.setText("Registrar Producto");
                listaEI.get(0).det.setText("nombre,categoria,preciov,precioc,stock,proveedor,fechaCaducidad,imagen");
                listaEI.get(0).ftr.setText("Tabla_Productos");
            }
            // EI 2: Modificar Producto (9 DETs, 1 FTR)
            if (listaEI.size() >= 2) {
                listaEI.get(1).name.setText("Modificar Producto");
                listaEI.get(1).det.setText("id,nombre,categoria,precioc,preciov,stock,proveedor,fechaCaducidad,imagen");
                listaEI.get(1).ftr.setText("Tabla_Productos");
            }
            // EI 3: Eliminar Producto (2 DETs, 1 FTR)
            if (listaEI.size() >= 3) {
                listaEI.get(2).name.setText("Eliminar Producto");
                listaEI.get(2).det.setText("idProducto,mensajeExito");
                listaEI.get(2).ftr.setText("Tabla_Productos");
            }
            // EI 4: Registrar Paciente (9 DETs, 1 FTR)
            if (listaEI.size() >= 4) {
                listaEI.get(3).name.setText("Registrar Paciente");
                listaEI.get(3).det.setText("id,nombreMascota,raza,edad,nombreDueno,telefono,descripcion,medicamentos,cajas");
                listaEI.get(3).ftr.setText("Tabla_Pacientes");
            }
            // EI 5: Modificar Paciente (8 DET, 1 FTR)
            if (listaEI.size() >= 5) {
                listaEI.get(4).name.setText("Modificar Paciente");
                listaEI.get(4).det.setText("nombreMascota,raza,edad,nombreDueno,telefono,descripcion,medicamento,cajas");
                listaEI.get(4).ftr.setText("Tabla_Pacientes");
            }
            // EI 6: Modificar Contrsena (2 DET, 1 FTR)
            if (listaEI.size() >= 5) {
                listaEI.get(5).name.setText("Modificar Contraseña");
                listaEI.get(5).det.setText("contrasenaAnterior,contranaActual");
                listaEI.get(5).ftr.setText("Tabla_Usuarios");
            }

            // =========================================================================
            // 7. RELLENAR VALORES DE SALIDAS EXTERNAS (EO) - Reportes con Lógica/Cálculo
            // =========================================================================
            // EO 1: Reporte Alertas Stock Bajo/Caducidad (5 DETs, 1 FTR)
            if (listaEO.size() >= 1) {
                listaEO.get(0).name.setText("Reporte Alertas Inventario");
                listaEO.get(0).det.setText("nombre,nombreprove,stock,estado,subtotal");
                listaEO.get(0).ftr.setText("Tabla_Productos");
            }
            // EO 2: Envío Automático de Pedidos por Correo (6 DETs, 1 FTRs)
            if (listaEO.size() >= 2) {
                listaEO.get(1).name.setText("Envío Correo Proveedor");
                listaEO.get(1).det.setText("nombreprod,nombreprov,correoProveedor,stock,estado,subtotal");
                listaEO.get(1).ftr.setText("Tabla_Pedidos");
            }
            // EO 3: Reporte Clínico en PDF (9 DETs, 1 FTR)
            if (listaEO.size() >= 3) {
                listaEO.get(2).name.setText("Exportar PDF Historial (FUR14)");
                listaEO.get(2).det.setText("id,nombreMascota,edad,raza,nombreDueno,telefono,descripcion,medicamentos,cajas");
                listaEO.get(2).ftr.setText("Tabla_Pacientes");
            }

            // =========================================================================
            // 8. RELLENAR VALORES DE CONSULTAS EXTERNAS (EQ) - Recuperación Directa
            // =========================================================================
            // EQ 1: Login de Usuario (3 DETs, 1 FTR)
            if (listaEQ.size() >= 1) {
                listaEQ.get(0).name.setText("Autenticación de Usuario");
                listaEQ.get(0).det.setText("username,password");
                listaEQ.get(0).ftr.setText("Tabla_Usuarios");
            }
            // EQ 2: Consultar Producto Filtros (7-9 DETs, 1 FTR)
            if (listaEQ.size() >= 2) {
                listaEQ.get(1).name.setText("Buscar Producto Filtros");
                listaEQ.get(1).det.setText("nombre,categoria,preciov,precioc,stock,proveedor,fechaCaducidad,imagen");
                listaEQ.get(1).ftr.setText("Tabla_Productos");
            }
            // EQ 3: Consultar Historial (6 DETs, 1 FTR)
            if (listaEQ.size() >= 3) {
                listaEQ.get(2).name.setText("Consultar Historial");
                listaEQ.get(2).det.setText("nombreproveedor,nombreproducto,estado,stock,subtotal,correo");
                listaEQ.get(2).ftr.setText("Tabla_Productos");
            }
            // EQ 4: Buscar Historial Clínico (8-10 DETs, 1 FTR)
            if (listaEQ.size() >= 4) {
                listaEQ.get(3).name.setText("Buscar Paciente");
                listaEQ.get(3).det.setText("NombrePaciente,fechaConsulta,id,raza,edad,nombreDueno,telefono,descripcion,medicamentos,cajas");
                listaEQ.get(3).ftr.setText("Tabla_Pacientes");
            }
            // EQ 5: Visualizar pedido (6 DETs, 2 FTRs)
            if (listaEQ.size() >= 5) {
                listaEQ.get(4).name.setText("Visualizar pedido");
                listaEQ.get(4).det.setText("nombreprod,nombreprov,stock,precionVenta,total,correo");
                listaEQ.get(4).ftr.setText("Tabla_Productos");
            }

            // =========================================================================
            // 9. CONFIGURAR VALORES DE LAS 14 GSCs (Sección 7 del PDF)
            // =========================================================================
            int[] valoresGSCs = {
                    3, // 1. Comunicación de datos (Usa APIs estándar)
                    0, // 2. Procesamiento distribuido (Preparación diferida)
                    3, // 3. Rendimiento (Tiempos de respuesta estrictos < 2 seg)
                    2, // 4. Configuración del equipamiento (Laptops estándar de la empresa)
                    2, // 5. Tasa de transacciones (Optimización de base de datos)
                    5, // 6. Entrada de datos en línea (24% al 30% interactivo)
                    4, // 7. Eficiencia del usuario final (Teclas rápidos/autocompletado)
                    3, // 8. Actualización en línea (Protección contra pérdidas)
                    3, // 9. Procesamiento complejo (Lógica extensa / If-Then-Else)
                    1, // 10. Reusabilidad (Código reusable internamente)
                    1, // 11. Facilidad de instalación (Instalación manual con guía)
                    2, // 12. Facilidad de operación (Alertas de error básicas)
                    1, // 13. Ubicaciones múltiples (Mismo hardware en sucursales)
                    2  // 14. Facilidad de cambio (Tablas de parámetros simples)
            };

            for (int i = 0; i < valoresGSCs.length && i < gruposDeCasillas.size(); i++) {
                int valorSeleccionado = valoresGSCs[i];
                if (valorSeleccionado >= 0 && valorSeleccionado <= 5) {
                    gruposDeCasillas.get(i)[valorSeleccionado].setSelected(true);
                }
            }

            txtResumen.setText("¡Métricas completas de la Veterinaria 'Bloom' cargadas con éxito!\n" +
                    "Se mapearon:\n" +
                    "- Funciones de Datos (ILF)\n" +
                    "- Entradas Externas (EI)\n" +
                    "- Salidas Externas (EO)\n" +
                    "- Consultas Externas (EQ)\n" +
                    "- Las 14 GSCs con los pesos del PDF.\n\n" +
                    "Ya puedes pulsar el botón 'Calcular Puntos de Función Totales'.");
        }
}