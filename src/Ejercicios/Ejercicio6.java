package Ejercicios;

import java.text.ParseException;
import java.util.Scanner;

public class Ejercicio6 {
	public static void main(String[] args) throws ParseException {
		Scanner sc = new Scanner(System.in);
		boolean sino = true;
		while (sino) {
			System.out.println("Menu:\n" + "1: Generar Archivo\n" + "2: Seleccionar fichero\n" + "3: Cargar alumno\n"
					+ "4: Mostrar alumno\n" + "5: Salir");
			int respuesta = sc.nextInt();
			switch (respuesta) {
			case 1:
				constructor6.generarfichero(sc);
				break;
			case 2:
				constructor6.seleccionarfichero(sc);
				break;
			case 3:
				constructor6.cargaralumno(sc);
				break;
			case 4:
				constructor6.mostraralumno();
				break;
			case 5:
				sino = false;
				break;
			}
		}
	}
}