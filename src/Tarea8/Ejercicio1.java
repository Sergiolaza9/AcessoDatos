package Tarea8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Ejercicio1 {

    public static void main(String[] args) {
        String hojaEstilo = "Tarea8/Alumnos.xsl"; // Ruta del archivo XSL
        String datosEmpleados = "Tarea8/Alumnos.xml"; // Ruta del archivo XML de entrada
        File pagHTML = new File("Tarea8/Pagina.html"); // Archivo HTML de salida

        // Crear o sobreescribir los archivos XML y XSL
        crearOActualizarArchivo(datosEmpleados, ContenidoXML.obtenerContenidoXML());
        crearOActualizarArchivo(hojaEstilo, ContenidoXSL.obtenerContenidoXSL());

        // Verificar si los archivos de entrada existen
        if (!new File(hojaEstilo).exists() || !new File(datosEmpleados).exists()) {
            System.err.println("Error: Uno o más archivos de entrada no existen.");
            return;
        }

        // Usar try-with-resources para asegurar el cierre del FileOutputStream
        try (FileOutputStream os = new FileOutputStream(pagHTML)) {
            // Crear las fuentes de los archivos XML y XSL
            Source datos = new StreamSource(datosEmpleados);
            Source estilos = new StreamSource(hojaEstilo);

            // Crear el resultado que será el archivo HTML
            Result result = new StreamResult(os);

            // Configurar el transformador con la hoja de estilo
            Transformer transformer = TransformerFactory.newInstance().newTransformer(estilos);

            // Realizar la transformación del XML en HTML
            transformer.transform(datos, result);

            System.out.println("Archivo HTML generado exitosamente: " + pagHTML.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error durante la transformación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para crear o actualizar el archivo con el contenido especificado
    private static void crearOActualizarArchivo(String rutaArchivo, String contenido) {
        File archivo = new File(rutaArchivo);
        try {
            if (archivo.exists()) {
                // Sobrescribir el archivo si ya existe
                Files.writeString(archivo.toPath(), contenido, StandardOpenOption.TRUNCATE_EXISTING);
            } else {
                // Crear el archivo si no existe
                archivo.getParentFile().mkdirs(); // Crea los directorios si no existen
                Files.writeString(archivo.toPath(), contenido, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            System.err.println("Error al crear o actualizar el archivo " + rutaArchivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
