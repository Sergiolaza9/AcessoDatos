package Ejercicios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio3_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Solicitar el archivo de texto
        System.out.println("Dime el fichero de texto:");
        String fichero = sc.next();

        // Validar que el archivo existe
        while (!validarArchivo(fichero)) {
            System.out.println("El archivo no existe o no es válido.");
            System.out.println("Dime el fichero de texto:");
            fichero = sc.next();
        }

        // Añadir nuevas líneas al final del archivo
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichero, true))) {
            System.out.println("Escribe lo que quieras añadir al archivo (escribe 'salir' para terminar):");
            sc.nextLine(); // Limpiar el buffer del scanner
            String linea;
            while (!(linea = sc.nextLine()).equalsIgnoreCase("salir")) {
                pw.println(linea);
            }

            System.out.println("El texto se ha añadido correctamente al archivo.");
        } catch (IOException e) {
            System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    /**
     * Valida si un archivo existe y es válido.
     *
     * @param fichero Ruta del archivo.
     * @return true si el archivo existe y es legible, false en caso contrario.
     */
    private static boolean validarArchivo(String fichero) {
        File archivo = new File(fichero);
        return archivo.exists() && archivo.isFile() && archivo.canWrite();
    }
}
