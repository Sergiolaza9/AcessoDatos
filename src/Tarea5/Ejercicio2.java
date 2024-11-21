package Tarea5;

import java.io.*;
import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Introduce el nombre del fichero");
            String nombreFichero = scanner.nextLine();

            FileInputStream fis = new FileInputStream(nombreFichero);
            ObjectInputStream ois = new ObjectInputStream(fis);

            System.out.println("Lista de alumnos:");
            while (true) {
                try {
                    Alumno alumno = (Alumno) ois.readObject();
                    System.out.println(alumno.toString());
                } catch (EOFException e) {
                    break; // Fin del fichero
                }
            }

            ois.close();
            fis.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
