package Tarea2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio2 {
public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	String ruta="Tarea2/Prueba2.txt";
	String linea;
	try(PrintWriter pw=new PrintWriter(new FileWriter(ruta))){
		System.out.println("Esribe lieas de texto, cuando quieras acabar escribe exit para finalizar");
		while(true) {
			linea=sc.nextLine();
			if(linea.equalsIgnoreCase("Exit")) {
				break;
			}
			pw.println(linea);
		}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
