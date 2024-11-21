package Ejercicios;

import java.io.Serializable;
import java.util.Date;

public class Alumno implements Serializable {
	private static final long serialVersionUID = 1L;
	int nia;
	String nombre, apellidos, ciclo, curso, grupo;
	char genero;
	Date fechanacimiento;

	public Alumno() {
		super();
	}

	public Alumno(int nia, String nombre, String apellidos, char genero, Date fechanacimiento, String ciclo,
			String curso, String grupo) {
		this.nia = nia;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.ciclo = ciclo;
		this.curso = curso;
		this.grupo = grupo;
		this.genero = genero;
		this.fechanacimiento = fechanacimiento;
	}

	// Getters y Setters
	public int getNia() {
		return nia;
	}

	public void setNia(int nia) {
		this.nia = nia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getcurso() {
		return curso;
	}

	public void setcurso(String curso) {
		this.curso = curso;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	@Override
	public String toString() {
		return "Alumno [nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero
				+ ", fechanacimiento=" + fechanacimiento + ", ciclo=" + ciclo + ", curso=" + curso + ", grupo=" + grupo
				+ "]";
	}

	
}
