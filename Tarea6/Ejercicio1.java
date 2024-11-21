package Tarea6;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class Alumno implements Serializable {
	private static final long serialVersionUID = 1L;
	private int nia;
	private String nombre;
	private String apellidos;
	private char genero;
	private Date fechaNacimiento;
	private String ciclo;
	private String curso;
	private String grupo;

	public Alumno(int nia, String nombre, String apellidos, char genero, Date fechaNacimiento, String ciclo,
			String curso, String grupo) {
		this.nia = nia;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.ciclo = ciclo;
		this.curso = curso;
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return "Alumno{NIA=" + nia + ", Nombre=" + nombre + ", Apellidos=" + apellidos + ", Género=" + genero
				+ ", Fecha de nacimiento=" + fechaNacimiento + ", Ciclo=" + ciclo + ", Curso=" + curso + ", Grupo="
				+ grupo + "}";
	}
}

public class Ejercicio1 {
	private static File archivoEnUso;
	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ParseException {
		boolean bucle = true;

		while (bucle) {
			System.out.println("\nMenú:");
			System.out.println("1: Generar un fichero vacío");
			System.out.println("2: Seleccionar fichero");
			System.out.println("3: Cargar Alumnos");
			System.out.println("4: Mostrar alumnos");
			System.out.println("5: Salir");
			System.out.print("Seleccione una opción: ");
			int respuesta = sc.nextInt();
			sc.nextLine();

			switch (respuesta) {
			case 1:
				crearFichero();
				break;
			case 2:
				seleccionarFichero();
				break;
			case 3:
				cargarAlumno();
				break;
			case 4:
				mostrarAlumnos();
				break;
			case 5: {
				System.out.println("Saliendo del menú.");
				bucle = false;
			}
			default:
				System.out.println("El número introducido es erróneo.");
			}
		}
	}

	private static void crearFichero() {
		System.out.print("Ingrese el nombre del fichero a crear: ");
		String nombreArchivo = sc.nextLine();
		String Carpeta="Tarea6\\";
		archivoEnUso = new File(Carpeta,nombreArchivo);
		

		try {
			if (archivoEnUso.createNewFile()) {
				System.out.println("Archivo creado: " + archivoEnUso.getName());
			} else {
				System.out.println("El archivo ya existe. Se usará el archivo existente.");
			}
		} catch (IOException e) {
			System.out.println("Error al crear el archivo.");
			e.printStackTrace();
		}
	}

	private static void seleccionarFichero() {
		System.out.print("Ingrese la ruta del fichero existente: ");
		String nombreArchivo = sc.nextLine();
		archivoEnUso = new File(nombreArchivo);

		if (archivoEnUso.exists() && archivoEnUso.isFile()) {
			System.out.println("Fichero seleccionado: " + archivoEnUso.getName());
		} else {
			System.out.println("El fichero no existe o no es válido.");
			archivoEnUso = null;
		}
	}

	private static void cargarAlumno() throws ParseException {
		if (archivoEnUso == null) {
			System.out.println("No hay fichero en uso. Seleccione o cree uno primero.");
			return;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		System.out.print("Ingrese el NIA del alumno: ");
		int nia = sc.nextInt();
		sc.nextLine();

		System.out.print("Ingrese el nombre del alumno: ");
		String nombre = sc.nextLine();

		System.out.print("Ingrese los apellidos del alumno: ");
		String apellidos = sc.nextLine();

		System.out.print("Ingrese el género del alumno (M/F): ");
		char genero = sc.nextLine().charAt(0);

		System.out.print("Ingrese la fecha de nacimiento (DD/MM/YYYY): ");
		Date fechaNacimiento = dateFormat.parse(sc.nextLine());

		System.out.print("Ingrese el ciclo del alumno: ");
		String ciclo = sc.nextLine();

		System.out.print("Ingrese el curso del alumno: ");
		String curso = sc.nextLine();

		System.out.print("Ingrese el grupo del alumno: ");
		String grupo = sc.nextLine();

		Alumno alumno = new Alumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoEnUso, true))) {
			out.writeObject(alumno);
			System.out.println("Alumno agregado exitosamente.");
		} catch (IOException e) {
			System.out.println("Error al escribir en el fichero.");
			e.printStackTrace();
		}
	}

	private static void mostrarAlumnos() {
		if (archivoEnUso == null) {
			System.out.println("No hay fichero en uso. Seleccione o cree uno primero.");
			return;
		}

		System.out.println("\nAlumnos en el fichero " + archivoEnUso.getName() + ":");
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivoEnUso))) {
			while (true) {
				Alumno alumno = (Alumno) in.readObject();
				System.out.println(alumno);
			}
		} catch (EOFException e) {
			System.out.println("Fin de la lista de alumnos.");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error al leer el fichero.");
			e.printStackTrace();
		}
	}
}
