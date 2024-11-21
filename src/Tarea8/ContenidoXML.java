package Tarea8;

public class ContenidoXML {
	public static String obtenerContenidoXML() {
		return """
				    <Alumnos>
				        <Alumno NIA="1">
				            <Nombre>Sergio</Nombre>
				            <Apellidos>Rascon</Apellidos>
				            <Genero>H</Genero>
				            <FechaNacimiento>18/10/2003</FechaNacimiento>
				            <Ciclo curso="1" Grupo="B">DAM</Ciclo>
				        </Alumno>
				        <Alumno NIA="2">
				            <Nombre>Ruben</Nombre>
				            <Apellidos>Garcia</Apellidos>
				            <Genero>H</Genero>
				            <FechaNacimiento>1/02/2005</FechaNacimiento>
				            <Ciclo curso="2" Grupo="A">DAM</Ciclo>
				        </Alumno>
				        <Alumno NIA="3">
				            <Nombre>Laura</Nombre>
				            <Apellidos>Martinez</Apellidos>
				            <Genero>M</Genero>
				            <FechaNacimiento>20/06/2010</FechaNacimiento>
				            <Ciclo curso="2" Grupo="G">DAM</Ciclo>
				        </Alumno>
				        <Alumno NIA="4">
				            <Nombre>Ana</Nombre>
				            <Apellidos>López</Apellidos>
				            <Genero>M</Genero>
				            <FechaNacimiento>15/09/2004</FechaNacimiento>
				            <Ciclo curso="1" Grupo="C">ASIR</Ciclo>
				        </Alumno>
				        <Alumno NIA="5">
				            <Nombre>Carlos</Nombre>
				            <Apellidos>Jiménez</Apellidos>
				            <Genero>H</Genero>
				            <FechaNacimiento>23/03/2002</FechaNacimiento>
				            <Ciclo curso="1" Grupo="B">DAW</Ciclo>
				        </Alumno>
				    </Alumnos>
				""";
	}
}
