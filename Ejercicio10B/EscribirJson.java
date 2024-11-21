package Ejercicio10B;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class EscribirJson {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            JSONArray alumnosArray = new JSONArray();

            // Pedir datos de 5 alumnos
            for (int i = 0; i < 1; i++) {
                System.out.println("Ingrese los datos del alumno " + (i + 1) + ":");

                System.out.print("NIA: ");
                int nia = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Apellidos: ");
                String apellidos = scanner.nextLine();

                System.out.print("Género (M/F): ");
                String genero = scanner.nextLine();

                System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
                String fechaNacimiento = scanner.nextLine();

                System.out.print("Ciclo: ");
                String ciclo = scanner.nextLine();

                System.out.print("Curso: ");
                String curso = scanner.nextLine();

                System.out.print("Grupo: ");
                String grupo = scanner.nextLine();

                // Crear un objeto JSON para el alumno
                JSONObject alumno = new JSONObject();
                alumno.put("NIA", nia);
                alumno.put("Nombre", nombre);
                alumno.put("Apellidos", apellidos);
                alumno.put("Genero", genero);
                alumno.put("FechaNacimiento", fechaNacimiento);
                alumno.put("Ciclo", ciclo);
                alumno.put("Curso", curso);
                alumno.put("Grupo", grupo);

                // Agregar el alumno al array
                alumnosArray.put(alumno);
            }

            // Crear el archivo JSON con los alumnos
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Alumnos", alumnosArray);

            String jsonString = jsonObject.toString(4); // Formatear con indentación de 4 espacios
            Files.write(Paths.get("alumnos.json"), jsonString.getBytes(), StandardOpenOption.CREATE);

            System.out.println("Archivo JSON creado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
