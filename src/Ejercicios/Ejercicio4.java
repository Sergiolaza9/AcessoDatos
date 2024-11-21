package Ejercicios;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio4 {
	public static void main(String[] args) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/YYYY");
		Scanner sc = new Scanner(System.in);
		System.out.println("Dime el fichero donde guardar a los alumnos");
		String fichero = sc.next();
		DataOutputStream os = null;
		try {
			os = new DataOutputStream(new FileOutputStream(fichero));
			for (int i = 0; i < 1; i++) {
				System.out.println("Dime el NIA del alumno");
				int nia = sc.nextInt();

				System.out.println("Dime el nombre del alumno");
				String nombre = sc.next();

				System.out.println("Dime los apellidos del alumno");
				String apellidos = sc.next();

				System.out.println("Dime el genero del alumno(M/F)");
				char genero = sc.next().charAt(0);
				sc.nextLine();

				System.out.println("Dime la Fecha de Nacimiento del alumno");
				Date fechaNacimiento = null;
				fechaNacimiento=dateFormat.parse(sc.nextLine());

				System.out.println("Dime el ciclo del alumno");
				String ciclo = sc.next();

				System.out.println("Dime el curso del alumno");
				String curso = sc.next();

				System.out.println("Dime el grupo del alumno");
				String grupo = sc.next();
				
				os.writeInt(nia);
				os.writeUTF(nombre);
				os.writeUTF(apellidos);
				os.writeChar(genero);
				os.writeUTF(dateFormat.format(fechaNacimiento));
				os.writeUTF(ciclo);
				os.writeUTF(curso);
				os.writeUTF(grupo);
				
				
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
