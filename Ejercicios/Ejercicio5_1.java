package Ejercicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Ejercicio5_1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Dime el nombre del fichero donde tienes a guardado a los alumnos");
		String fichero = sc.next();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fichero);
			ois = new ObjectInputStream(fis);

			while (true) {
				try {
					Alumno alumno = (Alumno) ois.readObject();
					System.out.println(alumno.toString());
				} catch (IOException | ClassNotFoundException e) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			try {
				ois.close();
				fis.close();
				sc.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
