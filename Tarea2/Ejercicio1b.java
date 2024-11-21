package Tarea2;

import java.util.Scanner;

public class Ejercicio1b {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String ruta;
		System.out.println("Dime la ruta del archivo");
		ruta = sc.next();
		sc.close();
		Ejercicio1a.leerarchivo(ruta);

	}
}
