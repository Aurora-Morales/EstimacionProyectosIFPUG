# Sistema para la estimacion de proyectos con IFPUG
Se desarrolló una aplicación de escritorio con javafx para calcular las métricas de IFPUG. Fue desarrollado en IntelliJ IDEA.

## Características para la ejecución
Es necesario tener instalado java versión 21 y javafx versión 21.
Instrucciones de uso
### Paso 1:
Llenar el campo de texto del tiempo que va a durar el proyecto en meses (puede ingresar valores como 1.5 en representación de un mes y medio). Es importante mencionar que este valor se toma en cuenta cuando se sabe exactamente en cuánto tiempo debe entregarse el producto, sin embargo también puede tomarse como una suposición para realizar el cálculo del número de personas necesarias para desarrollar el producto.

### Paso 2:
Llenar el campo de texto del número de personas en el equipo. Como en el paso anterior este valor se toma en cuenta cuando se sabe el número de trabajadores que hay en el equipo actualmente.

### Paso 3:
Ingresar la cantidad total de cada una de las funciones de datos ILF y/o EIF en los campos de texto correspondientes. Anteriormente se debieron haber calculado cuántas funciones ILF y/o EIF son necesarias para el proyecto.

### Paso 4:
Se crearán tarjetas dependiendo de la cantidad de funciones de datos del paso anterior. Estas tarjetas tienen tres campos de texto: Nombre de función de datos, los DET’s (se debe ingresar el nombre de cada DET separado por comas), los RET (se debe escribir el nombre de cada RET separado por comas).

### Paso 5:
Ingresar la cantidad de cada una de las funciones EI, EO, EQ en los campos de texto correspondientes. Igual que en paso anterior se debieron haber calculado el número funciones de cada transacción.

### Paso 6:
Como en el paso 4. Se crearán tarjetas dependiendo de la cantidad de funciones de transacciones del paso anterior. Estas tarjetas tienen tres campos de texto: Nombre de función de datos, los DET’s (se debe ingresar el nombre de cada DET separado por comas), los FTR (se debe escribir el nombre de cada FTR separado por comas).

### Paso 7:
Seleccionar solo una de cada una de las 14 características del sistema.

### Paso 8:
Presionar el botón verde “Calcular Puntos de Función Totales”.

### Paso 9 (Alternativo):
En caso de necesitar volver a empezar o realizar un nuevo cálculo presionar el botón rojo “Vaciar campos / Limpiar todo”.

### Paso 10 (Comprobación de resultados de este documentos):
Para cargar de forma automática los valores que se definieron en la sección de 5 - 7, se puede presionar el botón azul de “Precargar Datos de Prueba (PDF)”. Con los valores precargados, y/o modificados se debe presionar el botón para realizar el cálculo del paso 8.


