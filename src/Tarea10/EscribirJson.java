package Tarea10;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class EscribirJson {
    public static void main(String[] args) {
        try {
            // Crear un objeto JSON
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", "Juan");
            jsonObject.put("edad", 30);

            // Convertir el objeto JSON a String y escribirlo en un archivo
            String jsonString = jsonObject.toString(4); // El parámetro 4 es para formatearlo con indentación de 4 espacios
            Files.write(Paths.get("ruta/al/archivo.json"), jsonString.getBytes(), StandardOpenOption.CREATE);

            System.out.println("Archivo JSON creado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}