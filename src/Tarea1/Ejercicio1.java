package Tarea1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ejercicio1 {
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		Alumnos[] alumnos = new Alumnos[5];

		for (int i = 0; i < alumnos.length; i++) {
			System.out.println("Introduce los datos del alumno " + (i + 1));

			System.out.print("NIA:");
			int nia = entrada.nextInt();
			entrada.nextLine();

			System.out.print("Nombre:");
			String nombre = entrada.nextLine();

			System.out.print("Apellidos:");
			String apellidos = entrada.nextLine();

			System.out.print("Genero:");
			char genero = entrada.nextLine().charAt(0);

			System.out.print("Fecha Nacimiento(dd-mm-yyyy):");
			String fecha = entrada.nextLine();
			LocalDate fechanac = LocalDate.parse(fecha, formatter);

			System.out.print("Ciclo:");
			String Ciclo = entrada.nextLine();

			System.out.print("Curso:");
			String curso = entrada.nextLine();

			System.out.print("Grupo");
			String grupo = entrada.nextLine();

			alumnos[i] = new Alumnos(nia, nombre, apellidos, genero, fechanac, Ciclo, curso, grupo);

		}
		System.out.println("\nDatos de los alumnos ingresados:");
		for (Alumnos alumno : alumnos) {
			System.out.println("-------------------");
			System.out.println(alumno);
		}
		
	}
}
