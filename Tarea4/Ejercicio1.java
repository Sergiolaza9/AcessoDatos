package Tarea4;

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Clase Alumno que representa los datos de un alumno. Esta clase implementa
 * Serializable para permitir la serialización de objetos Alumno.
 * 
 * @author Sergio Rascon
 */
class Alumno implements Serializable {
	private static final long serialVersionUID = 1L; // Identificador de serialización
	private int NIA;
	private String nombre;
	private String apellidos;
	private char genero;
	private Date fechaNacimiento;
	private String ciclo;
	private String curso;
	private String grupo;

	/**
	 * Constructor de la clase Alumno.
	 * 
	 * @param NIA             Número de Identificación del Alumno
	 * @param nombre          Nombre del alumno
	 * @param apellidos       Apellidos del alumno
	 * @param genero          Género del alumno ('M' o 'F')
	 * @param fechaNacimiento Fecha de nacimiento del alumno
	 * @param ciclo           Ciclo formativo del alumno
	 * @param curso           Curso en el que está matriculado el alumno
	 * @param grupo           Grupo del alumno
	 */
	public Alumno(int NIA, String nombre, String apellidos, char genero, Date fechaNacimiento, String ciclo,
			String curso, String grupo) {
		this.NIA = NIA;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.ciclo = ciclo;
		this.curso = curso;
		this.grupo = grupo;
	}

	/**
	 * Sobreescribe el método toString() para devolver una representación en String
	 * del objeto Alumno.
	 * 
	 * @return Una cadena que representa los datos del alumno
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "NIA: " + NIA + "\nNombre: " + nombre + "\nApellidos: " + apellidos + "\nGenero: " + genero
				+ "\nFecha de Nacimiento: " + sdf.format(fechaNacimiento) + "\nCiclo: " + ciclo + "\nCurso: " + curso
				+ "\nGrupo: " + grupo + "\n";
	}
}

/**
 * Clase principal para gestionar el registro de alumnos. Esta clase permite
 * guardar los datos de 5 alumnos en un archivo binario.
 * 
 * @author Sergio Rascon
 */
public class Ejercicio1 {
	/**
	 * Método principal que ejecuta el programa. Lee los datos de 5 alumnos, los
	 * guarda en un archivo binario y los almacena de forma serializada.
	 * 
	 * @param args Argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		// Se utiliza para leer la entrada del usuario
		Scanner sc = new Scanner(System.in);

		// Solicita el nombre del archivo donde se guardarán los datos
		System.out.print("Introduce la ruta y nombre del fichero donde se guardarán los alumnos: ");
		String nombreArchivo = sc.nextLine();

		// Uso de ObjectOutputStream para escribir los objetos Alumno en el archivo
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {

			// Bucle para introducir los datos de 5 alumnos
			for (int i = 0; i < 5; i++) {
				System.out.println("Introduce los datos del alumno " + (i + 1) + ":");

				// Lectura de los datos del alumno
				System.out.print("NIA: ");
				int NIA = sc.nextInt();
				sc.nextLine(); // Consumir el salto de línea pendiente

				System.out.print("Nombre: ");
				String nombre = sc.nextLine();

				System.out.print("Apellidos: ");
				String apellidos = sc.nextLine();

				System.out.print("Genero (M/F): ");
				char genero = sc.next().charAt(0);
				sc.nextLine(); // Consumir el salto de línea pendiente

				System.out.print("Fecha de Nacimiento (dd/MM/yyyy): ");
				String fechaNacimientoStr = sc.nextLine();
				// Convierte la fecha de String a Date
				Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimientoStr);

				System.out.print("Ciclo: ");
				String ciclo = sc.nextLine();

				System.out.print("Curso: ");
				String curso = sc.nextLine();

				System.out.print("Grupo: ");
				String grupo = sc.nextLine();

				// Creación del objeto Alumno con los datos ingresados
				Alumno alumno = new Alumno(NIA, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);

				// Guarda el objeto Alumno en el archivo binario
				oos.writeObject(alumno);
				System.out.println("Alumno guardado correctamente.\n");
			}
		} catch (Exception e) {
			// Manejo de excepciones (IOException, ParseException, etc.)
			e.printStackTrace();
		}
		System.out.println("Datos de los alumnos guardados correctamente en el fichero.");
		sc.close();
	}
}
