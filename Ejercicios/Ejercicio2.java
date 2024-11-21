package Ejercicios;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String linea;
		String ruta=("Hola.txt");
		PrintWriter pw=null;
		try {
			pw=new PrintWriter(ruta);
			System.out.println("Escribe todo lo que quieras cuando quieras salir exit");
			while(true) {
				linea=sc.nextLine();
				if(linea.equalsIgnoreCase("Exit")) {
					System.out.println("Finalizacion del programa");
					break;
				}
				pw.println(linea);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pw.close();
		}
	}
}
