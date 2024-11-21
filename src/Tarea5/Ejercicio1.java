package Tarea5;

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

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

    public Alumno(int nia, String nombre, String apellidos, char genero, Date fechaNacimiento, String ciclo, String curso, String grupo) {
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "NIA: " + nia + ", Nombre: " + nombre + " " + apellidos + ", Género: " + genero +
               ", Fecha de Nacimiento: " + sdf.format(fechaNacimiento) + ", Ciclo: " + ciclo +
               ", Curso: " + curso + ", Grupo: " + grupo;
    }
}

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.print("Introduce el nombre del fichero donde se guardarán los alumnos: ");
            String nombreFichero = scanner.nextLine();

            FileOutputStream fos = new FileOutputStream(nombreFichero);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (int i = 0; i < 5; i++) {
                System.out.println("Introduce los datos del alumno " + (i + 1) + ":");
                
                System.out.print("NIA: ");
                int nia = Integer.parseInt(scanner.nextLine());
                
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                
                System.out.print("Apellidos: ");
                String apellidos = scanner.nextLine();
                
                System.out.print("Género (M/F): ");
                char genero = scanner.nextLine().charAt(0);
                
                System.out.print("Fecha de Nacimiento (dd/MM/yyyy): ");
                Date fechaNacimiento = dateFormat.parse(scanner.nextLine());
                
                System.out.print("Ciclo: ");
                String ciclo = scanner.nextLine();
                
                System.out.print("Curso: ");
                String curso = scanner.nextLine();
                
                System.out.print("Grupo: ");
                String grupo = scanner.nextLine();

                Alumno alumno = new Alumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);

                oos.writeObject(alumno);
            }

            oos.close();
            fos.close();
            System.out.println("Alumnos guardados correctamente en el fichero " + nombreFichero);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
