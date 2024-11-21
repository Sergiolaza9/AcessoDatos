package Ejercicios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio5 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Dime el fichero donde guardar a los alumnos");
		String fichero = sc.next();
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(fichero));
			for (int i = 0; i < 1; i++) {
				System.out.println("Dime el NIA del alumno");
				int nia = sc.nextInt();

				System.out.println("Dime el nombre del alumno");
				String nombre = sc.next();

				System.out.println("Dime los apellidos del alumno");
				String apellidos = sc.next();

				System.out.println("Dime el genero del alumno(M/F)");
				char genero = sc.next().charAt(0);

				System.out.println("Dime la Fecha de Nacimiento del alumno");
				String fechanac = sc.next();
				Date fechaNacimiento = (new SimpleDateFormat("dd/MM/yyyy").parse(fechanac));

				System.out.println("Dime el ciclo del alumno");
				String ciclo = sc.next();

				System.out.println("Dime el curso del alumno");
				String curso = sc.next();

				System.out.println("Dime el grupo del alumno");
				String grupo = sc.next();

				Alumno alumno = new Alumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);
				os.writeObject(alumno);
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.close();
				sc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Alumno guardado");
	}
}
