package Tarea2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1a {

	public static void main(String[] args) {
		// Ruta del archivo que quieres leer (en este caso archivo.txt)
		String ruta = "Tarea2/Prueba.txt";
		leerarchivo(ruta);

	}

	public static void leerarchivo(String ruta) {
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(ruta));
			String linea;

			// Leer y mostrar el contenido del archivo línea por línea
			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {

				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
