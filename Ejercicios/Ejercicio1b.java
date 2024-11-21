package Ejercicios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio1b {
	public static void main(String[] args) {
			Scanner sc=new Scanner(System.in);
			String linea;
			String ruta;
			BufferedReader br=null;
			System.out.println("Dime la ruta del archivo con el nombre incluido");
			ruta=sc.next();
			
			try {
				br=new BufferedReader(new FileReader(ruta));
				while((linea= br.readLine())!=null) {
					System.out.println(linea);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
