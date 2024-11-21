package Ejercicio10B;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class LeerJson {
    public static void main(String[] args) {
        try {
            // Leer el archivo JSON como String
            String content = new String(Files.readAllBytes(Paths.get("alumnos.json")));

            // Convertir el String a un objeto JSONObject
            JSONObject jsonObject = new JSONObject(content);

            // Obtener el array de alumnos
            JSONArray alumnosArray = jsonObject.getJSONArray("Alumnos");

            // Mostrar los datos de los alumnos
            for (int i = 0; i < alumnosArray.length(); i++) {
                JSONObject alumno = alumnosArray.getJSONObject(i);

                System.out.println("Alumno " + (i + 1) + ":");
                System.out.println("  NIA: " + alumno.getInt("NIA"));
                System.out.println("  Nombre: " + alumno.getString("Nombre"));
                System.out.println("  Apellidos: " + alumno.getString("Apellidos"));
                System.out.println("  Género: " + alumno.getString("Genero"));
                System.out.println("  Fecha de Nacimiento: " + alumno.getString("FechaNacimiento"));
                System.out.println("  Ciclo: " + alumno.getString("Ciclo"));
                System.out.println("  Curso: " + alumno.getString("Curso"));
                System.out.println("  Grupo: " + alumno.getString("Grupo"));
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
