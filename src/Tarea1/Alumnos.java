package Tarea1;

import java.time.LocalDate;
import java.util.Date;

public class Alumnos {
	String nombre, apellidos, ciclo, curso, grupo;
	int  nia;
	char genero;
	LocalDate fechanac;

	public Alumnos(int nia, String nombre, String apellidos, char genero, LocalDate fechanac, String ciclo, String curso, String grupo) {
		super();
		this.nia = nia;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.ciclo = ciclo;
		this.curso = curso;
		this.grupo = grupo;
		this.genero = genero;
		this.fechanac = fechanac;
	}
	@Override
	public String toString() {
		return "alumnos [nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", ciclo=" + ciclo
				+ ", curso=" + curso + ", grupo=" + grupo + ", genero=" + genero + ", fechanac=" + fechanac + "]";
	}

	
}
