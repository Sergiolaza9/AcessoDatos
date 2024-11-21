package Ejercicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio3_2 {
	public static void main(String[] args) {
		String linea1;
		String linea2;
		Scanner sc = new Scanner(System.in);
		System.out.println("Dime el primer fichero de lectura");
		String lectura1 = sc.next();
		System.out.println("Dime el segundo fichero de lectura");
		String lectura2 = sc.next();
		System.out.println("Dime el fichero de escritura");
		String escritura = sc.next();
		BufferedReader br = null;
		BufferedReader br1 = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new FileReader(lectura1));
			br1 = new BufferedReader(new FileReader(lectura2));
			pw = new PrintWriter(new FileWriter(escritura));
			while ((linea1 = br.readLine()) != null && (linea2 = br1.readLine()) != null) {
				pw.println(linea1);
				pw.println(linea2);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				br1.close();
				br.close();
				pw.close();
				sc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Los archivos se han copiado");
	}
}
