package Tarea3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio1 {
public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	//Entrada.txt
	System.out.println("Dime la ruta del archivo que quieres leer");
	String rutaentrada=sc.next();
	//Salida.txt
	System.out.println("Dime la ruta del archivo que quieres escribir");
	String rutasalida=sc.next();
	try(
		BufferedReader br=new BufferedReader(new FileReader(rutaentrada));
		PrintWriter pw=new PrintWriter(new FileWriter(rutasalida));
			){
		String linea;
		int numerolinea=1;
		while((linea=br.readLine())!=null){
		if(numerolinea%2==0) {
			pw.println(linea);
		}
		numerolinea++;	
	}
		System.out.println("Se han guardado las lineas par en el fichero");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	sc.close();
}
}

