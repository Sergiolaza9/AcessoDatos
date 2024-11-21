package Tarea10;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeerJson {
    public static void main(String[] args) {
        try {
            // Leer el archivo JSON como String
            String content = new String(Files.readAllBytes(Paths.get("ruta/al/archivo.json")));

            // Convertir el String a un objeto JSONObject
            JSONObject jsonObject = new JSONObject(content);

            // Acceder a datos en el JSON
            String nombre = jsonObject.getString("nombre");
            int edad = jsonObject.getInt("edad");

            System.out.println("Nombre: " + nombre);
            System.out.println("Edad: " + edad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
