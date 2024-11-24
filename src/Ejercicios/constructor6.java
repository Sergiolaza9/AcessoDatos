package Ejercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class constructor6 {
	private static File archivo;

	public static void generarfichero(Scanner sc) {
		System.out.println("Dime el fichero que quieres generar");
		String fichero = sc.next();
		archivo = new File(fichero);
		try {
			if (archivo.createNewFile()) {
				System.out.println("El archivo ha sido creado");
			} else {
				System.out.println("El archivo ya existe y ha sido seleccionado");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void seleccionarfichero(Scanner sc) {
		System.out.println("Dime el fichero que quieres seleccionar");
		String fichero = sc.next();

		if (archivo.exists() && archivo.canRead()) {
			archivo = new File(fichero);
			System.out.println("El archivo se ha seleccionado");
		} else {
			System.out.println("El archivo no existe o no se puede leer");
			archivo = null;
		}
	}

	public static void cargaralumno(Scanner sc) throws ParseException {
		if (archivo == null) {
			System.out.println("No hay archivo seleccionado");
			return;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		System.out.println("Dime el Nia del alumno");
		int nia = sc.nextInt();

		System.out.println("Dime el nombre del alumno");
		String nombre = sc.next();

		System.out.println("Dime los apellidos del alumno");
		String apellidos = sc.next();

		System.out.println("Dime el genero del alumno");
		char genero = sc.next().charAt(0);

		System.out.println("Dime la fecha de nacimiento del alumno");
		Date fecha = dateFormat.parse(sc.next());

		System.out.println("Dime el curso del alumno");
		String curso = sc.next();

		System.out.println("Dime el ciclo del alumno");
		String ciclo = sc.next();

		System.out.println("Dime el grupo del alumno");
		String grupo = sc.next();

		Alumno alumno = new Alumno(nia, nombre, apellidos, genero, fecha, curso, ciclo, grupo);

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(archivo));
			oos.writeObject(alumno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void mostraralumno() {
		if (archivo == null) {
			System.out.println("No hay archivo seleccionado");
			return;

		}
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(archivo));
			while(true) {
				Alumno alumno=(Alumno) ois.readObject();
				System.out.println(alumno);
			}
		} catch (Exception e) {
			System.out.println("Fin del archivo");
		}
	}
}
