package Ejer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejer2_1 {
	public static void main(String[] args) throws IOException {
		Scanner sc=new Scanner(System.in);
		System.out.println("Dime la ruta del archivo");
		String ruta=sc.next();
		System.out.println(ruta);
		String linea;
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(ruta));
			while((linea=br.readLine())!=null) {
				System.out.println(linea);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			br.close();
			sc.close();
		}
	}
}
