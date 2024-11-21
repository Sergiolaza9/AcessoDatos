package Tarea11;

import java.sql.Date;

public class Alumno {
	int nia;
	String nombre;
	String apellidos;
	String genero;
	Date fechaNacimiento;
	String ciclo;
	String curso;
	String grupo;

	public Alumno(int nia, String nombre, String apellidos, String genero, Date fechaNacimiento, String ciclo,
			String curso, String grupo) {
		this.nia = nia;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.ciclo = ciclo;
		this.curso = curso;
		this.grupo = grupo;
	}

	// Getters y Setters
}
