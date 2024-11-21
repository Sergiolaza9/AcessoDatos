package Ejercicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio3_1 {
	public static void main(String[] args) {
		String linea;
		Scanner sc=new Scanner(System.in);
		System.out.println("Dime la ruta del archivo a leer");
		String rutae=sc.next();
		System.out.println("Dime la ruta del archivo a escribir");
		String rutas=sc.next();
		BufferedReader br=null;
		PrintWriter pw=null;
		try {
			int linea1=0;
			br=new BufferedReader(new FileReader(rutae));
			pw=new PrintWriter(rutas);
			while((linea=br.readLine())!=null) {
				if(linea1%2==0) {
					pw.println(linea);
				}
				linea1++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				br.close();
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
}
