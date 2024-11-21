package Tarea3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Entrada1.txt
		System.out.println("Dime la ruta del primer fichero de lectura");
		String lectura1 = sc.next();
		// Entrada2.txt
		System.out.println("Dime la ruta del segundo fichero de lectura");
		String lectura2 = sc.next();
		// Escritura1.txt
		System.out.println("Dime la ruta del archivo de escritura");
		String escritura = sc.next();

		try (BufferedReader br1 = new BufferedReader(new FileReader(lectura1));
				BufferedReader br2 = new BufferedReader(new FileReader(lectura2));
				PrintWriter pw = new PrintWriter(new FileWriter(escritura));

		) {
			String linea1 = null; // Inicializamos a null
			String linea2 = null; // Inicializamos a null

			// Leer líneas de ambos ficheros de lectura de forma alternada
			while (true) {
				linea1 = br1.readLine();
				linea2 = br2.readLine();
				if (linea1 == null || linea2 == null) {
					break;
				}
				if (linea1 != null) {
					pw.println(linea1); // Escribir línea del primer fichero
				}
				if (linea2 != null) {
					pw.println(linea2); // Escribir línea del segundo fichero
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
	}
}
