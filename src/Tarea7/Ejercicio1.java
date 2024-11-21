package Tarea7;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Scanner;

public class Ejercicio1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			Element root = document.createElement("alumnos");
			document.appendChild(root);

			for (int i = 1; i <= 5; i++) {
				System.out.println("Ingrese los datos del alumno " + i + ":");

				System.out.print("NIA (número entero): ");
				int nia = sc.nextInt();

				System.out.print("Nombre: ");
				String nombre = sc.next();

				System.out.print("Apellidos: ");
				String apellidos = sc.next();

				System.out.print("Genero (M/F): ");
				char genero = sc.next().charAt(0);

				System.out.print("Fecha de Nacimiento (dd-mm-yyyy): ");
				String fechaNacimiento = sc.next();

				System.out.print("Ciclo: ");
				String ciclo = sc.next();

				System.out.print("Curso: ");
				String curso = sc.next();

				System.out.print("Grupo: ");
				String grupo = sc.next();

				crearElementoAlumno(document, root, nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso,
						grupo);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);

			StreamResult streamResult = new StreamResult(new File("Tarea7//alumnos.xml"));
			transformer.transform(domSource, streamResult);

			System.out.println("Archivo XML 'alumnos.xml' generado con éxito");

		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}

	private static void crearElementoAlumno(Document document, Element root, int nia, String nombre, String apellidos,
			char genero, String fechaNacimiento, String ciclo, String curso, String grupo) {

		Element alumno = document.createElement("alumno");
		root.appendChild(alumno);

		Element niaElement = document.createElement("nia");
		niaElement.appendChild(document.createTextNode(String.valueOf(nia)));
		alumno.appendChild(niaElement);

		Element nombreElement = document.createElement("nombre");
		nombreElement.appendChild(document.createTextNode(nombre));
		alumno.appendChild(nombreElement);

		Element apellidosElement = document.createElement("apellidos");
		apellidosElement.appendChild(document.createTextNode(apellidos));
		alumno.appendChild(apellidosElement);

		Element generoElement = document.createElement("genero");
		generoElement.appendChild(document.createTextNode(String.valueOf(genero)));
		alumno.appendChild(generoElement);

		Element fechaNacimientoElement = document.createElement("fechaNacimiento");
		fechaNacimientoElement.appendChild(document.createTextNode(fechaNacimiento));
		alumno.appendChild(fechaNacimientoElement);

		Element cicloElement = document.createElement("ciclo");
		cicloElement.appendChild(document.createTextNode(ciclo));
		alumno.appendChild(cicloElement);

		Element cursoElement = document.createElement("curso");
		cursoElement.appendChild(document.createTextNode(curso));
		alumno.appendChild(cursoElement);

		Element grupoElement = document.createElement("grupo");
		grupoElement.appendChild(document.createTextNode(grupo));
		alumno.appendChild(grupoElement);
	}
}
