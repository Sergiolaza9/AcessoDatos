package Tarea11;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.*;
import java.nio.file.Files;

public class MenuAlumno {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/Alumnos07";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "manager";

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			while (true) {
				System.out.println("\nMenú de opciones:");
				System.out.println("1. Insertar un nuevo alumno");
				System.out.println("2. Insertar un nuevo grupo");
				System.out.println("3. Mostrar todos los alumnos con su grupo");
				System.out.println("4. Guardar alumnos en fichero");
				System.out.println("5. Leer alumnos de fichero y guardarlos en la BD");
				System.out.println("6. Modificar el nombre de un alumno por NIA");
				System.out.println("7. Eliminar un alumno por NIA");
				System.out.println("8. Eliminar alumnos por curso");
				System.out.println("9. Guardar grupos y sus alumnos en XML/JSON");
				System.out.println("10. Leer grupos de XML/JSON y guardarlos en la BD");
				System.out.println("0. Salir");
				System.out.print("Selecciona una opción: ");
				int opcion = scanner.nextInt();
				scanner.nextLine(); // Consumir nueva línea

				switch (opcion) {
				case 1 -> insertarAlumno(scanner, connection);
				case 2 -> insertarGrupo(scanner, connection);
				case 3 -> mostrarAlumnosConGrupo(connection);
				case 4 -> guardarAlumnoEnFichero(connection, scanner);
				case 5 -> leerAlumnosDeFicheroYGuardar(connection, scanner);
				case 6 -> modificarAlumno(connection, scanner);
				case 7 -> eliminarAlumnoPorNIA(connection, scanner);
				case 8 -> eliminarAlumnosPorCurso(connection, scanner);
				case 9 -> guardarGruposEnXMLoJSON(connection, scanner);
				case 10 -> leerGruposDeXMLoJSONYGuardar(connection, scanner);
				case 0 -> {
					System.out.println("Saliendo...");
					connection.close();
					return;
				}
				default -> System.out.println("Opción inválida, intenta nuevamente.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertarAlumno(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Introduce el NIA del alumno: ");
		int nia = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Introduce el nombre del alumno: ");
		String nombre = scanner.nextLine();
		System.out.print("Introduce los apellidos del alumno: ");
		String apellidos = scanner.nextLine();
		System.out.print("Introduce el género del alumno: ");
		String genero = scanner.nextLine();
		System.out.print("Introduce la fecha de nacimiento (YYYY-MM-DD): ");
		String fechaNacimiento = scanner.nextLine();
		System.out.print("Introduce el ID del grupo al que pertenece: ");
		int grupoId = scanner.nextInt();

		String sql = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_de_nacimiento, id_grupo) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, nia);
			statement.setString(2, nombre);
			statement.setString(3, apellidos);
			statement.setString(4, genero);
			statement.setDate(5, Date.valueOf(fechaNacimiento));
			statement.setInt(6, grupoId);
			statement.executeUpdate();
			System.out.println("Alumno insertado correctamente.");
		}
	}

	private static void insertarGrupo(Scanner scanner, Connection connection) throws SQLException {
		System.out.print("Introduce el ID del grupo: ");
		int id = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Introduce el nombre del grupo: ");
		String nombre = scanner.nextLine();
		System.out.print("Introduce el ciclo del grupo: ");
		String ciclo = scanner.nextLine();
		System.out.print("Introduce el curso del grupo: ");
		String curso = scanner.nextLine();

		String sql = "INSERT INTO grupo (id_grupo, nombre_grupo, ciclo, curso) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.setString(2, nombre);
			statement.setString(3, ciclo);
			statement.setString(4, curso);
			statement.executeUpdate();
			System.out.println("Grupo insertado correctamente.");
		}
	}

	private static void mostrarAlumnosConGrupo(Connection connection) throws SQLException {
		String sql = "SELECT a.NIA, a.Nombre, a.Apellidos, a.Genero, a.Fecha_de_nacimiento, g.nombre_grupo_grupo AS Grupo, g.ciclo, g.curso "
				+ "FROM alumno a JOIN grupo g ON a.id_grupo = g.id_grupo";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				System.out.printf(
						"NIA: %d, Nombre: %s, Apellidos: %s, Género: %s, Fecha de nacimiento: %s, Grupo: %s, Ciclo: %s, Curso: %s%n",
						resultSet.getInt("NIA"), resultSet.getString("Nombre"), resultSet.getString("Apellidos"),
						resultSet.getString("Genero"), resultSet.getDate("Fecha_de_nacimiento"),
						resultSet.getString("Grupo"), resultSet.getString("Ciclo"), resultSet.getString("Curso"));
			}
		}
	}

	private static void guardarAlumnoEnFichero(Connection connection, Scanner scanner) {
		System.out.print("Introduce el nombre del fichero donde se guardarán los alumnos: ");
		String nombreFichero = scanner.nextLine();

		String sql = "SELECT NIA, Nombre, Apellidos, Genero, Fecha_de_nacimiento, id_grupo FROM alumno";

		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				PrintWriter writer = new PrintWriter(new FileWriter(nombreFichero))) {

			while (resultSet.next()) {
				writer.printf("%d;%s;%s;%s;%s;%d%n", resultSet.getInt("NIA"), resultSet.getString("Nombre"),
						resultSet.getString("Apellidos"), resultSet.getString("Genero"),
						resultSet.getDate("Fecha_de_nacimiento"), resultSet.getInt("id_grupo"));
			}
			System.out.println("Alumnos guardados correctamente en el fichero: " + nombreFichero);
		} catch (IOException e) {
			System.out.println("Error al escribir en el fichero: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al obtener los datos de los alumnos: " + e.getMessage());
		}
	}

	private static void modificarAlumno(Connection connection, Scanner scanner) {
		System.out.print("Introduce el NIA del alumno a modificar: ");
		int nia = scanner.nextInt();
		scanner.nextLine(); // Consumir la nueva línea

		System.out.print("Introduce el nuevo nombre del alumno: ");
		String nuevoNombre = scanner.nextLine();

		String sql = "UPDATE alumno SET Nombre = ? WHERE NIA = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, nuevoNombre);
			preparedStatement.setInt(2, nia);

			int filasAfectadas = preparedStatement.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Nombre del alumno actualizado correctamente.");
			} else {
				System.out.println("No se encontró un alumno con el NIA proporcionado.");
			}
		} catch (SQLException e) {
			System.out.println("Error al modificar el nombre del alumno: " + e.getMessage());
		}
	}

	private static void leerAlumnosDeFicheroYGuardar(Connection connection, Scanner scanner) {
		System.out.print("Introduce el nombre del fichero que contiene los alumnos: ");
		String nombreFichero = scanner.nextLine();

		try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
			String linea;

			String sql = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_de_nacimiento, id_grupo) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				while ((linea = reader.readLine()) != null) {
					String[] partes = linea.split(";");

					int nia = Integer.parseInt(partes[0]);
					String nombre = partes[1];
					String apellidos = partes[2];
					String genero = partes[3];
					Date fechaNacimiento = Date.valueOf(partes[4]);
					int grupoId = Integer.parseInt(partes[5]);

					preparedStatement.setInt(1, nia);
					preparedStatement.setString(2, nombre);
					preparedStatement.setString(3, apellidos);
					preparedStatement.setString(4, genero);
					preparedStatement.setDate(5, fechaNacimiento);
					preparedStatement.setInt(6, grupoId);

					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
				System.out.println("Alumnos cargados desde el fichero y guardados en la base de datos.");
			}
		} catch (IOException | SQLException e) {
			System.out.println("Error al leer el fichero o guardar los datos: " + e.getMessage());
		}
	}

	private static void eliminarAlumnoPorNIA(Connection connection, Scanner scanner) {
		System.out.print("Introduce el NIA del alumno a eliminar: ");
		int nia = scanner.nextInt();

		String sql = "DELETE FROM alumno WHERE NIA = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, nia);

			int filasAfectadas = preparedStatement.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Alumno eliminado correctamente.");
			} else {
				System.out.println("No se encontró un alumno con el NIA proporcionado.");
			}
		} catch (SQLException e) {
			System.out.println("Error al eliminar el alumno: " + e.getMessage());
		}
	}

	private static void eliminarAlumnosPorCurso(Connection connection, Scanner scanner) {
		System.out.print("Introduce el curso de los alumnos a eliminar: ");
		String curso = scanner.nextLine();

		String sql = "DELETE a FROM alumno a JOIN grupo g ON a.id_grupo = g.id_grupo WHERE g.curso = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, curso);

			int filasAfectadas = preparedStatement.executeUpdate();
			System.out.println(filasAfectadas + " alumno(s) eliminado(s) del curso " + curso);
		} catch (SQLException e) {
			System.out.println("Error al eliminar alumnos del curso: " + e.getMessage());
		}
	}

	private static void guardarEnJSON(Connection connection, String sql) throws SQLException {
		JSONArray gruposArray = new JSONArray();
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				int grupoId = resultSet.getInt("id_grupo");
				String grupoNombre = resultSet.getString("nombre_grupo");
				String ciclo = resultSet.getString("ciclo");
				String curso = resultSet.getString("curso");

				JSONObject grupo = new JSONObject();
				grupo.put("id_grupo", grupoId);
				grupo.put("nombre_grupo", grupoNombre);
				grupo.put("ciclo", ciclo);
				grupo.put("curso", curso);

				JSONArray alumnosArray = new JSONArray();
				do {
					if (resultSet.getInt("id_grupo") == grupoId) {
						JSONObject alumno = new JSONObject();
						alumno.put("NIA", resultSet.getInt("NIA"));
						alumno.put("Nombre", resultSet.getString("Nombre"));
						alumno.put("Apellidos", resultSet.getString("Apellidos"));
						alumno.put("Genero", resultSet.getString("Genero"));
						alumno.put("Fecha_de_nacimiento", resultSet.getDate("Fecha_de_nacimiento"));
						alumnosArray.put(alumno);
					}
				} while (resultSet.next() && resultSet.getInt("id_grupo") == grupoId);

				grupo.put("alumnos", alumnosArray);
				gruposArray.put(grupo);
			}
		}

		try (FileWriter file = new FileWriter("grupos.json")) {
			file.write(gruposArray.toString(4));
			System.out.println("Datos guardados en formato JSON.");
		} catch (IOException e) {
			System.out.println("Error al guardar los datos en JSON: " + e.getMessage());
		}
	}

	private static void guardarEnXML(Connection connection, String sql) throws SQLException {
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElement("grupos");
			doc.appendChild(root);

			while (resultSet.next()) {
				int grupoId = resultSet.getInt("id_grupo");
				String grupoNombre = resultSet.getString("nombre_grupo");
				String ciclo = resultSet.getString("ciclo");
				String curso = resultSet.getString("curso");

				Element grupo = doc.createElement("grupo");
				grupo.setAttribute("id_grupo", String.valueOf(grupoId));

				Element nombreGrupo = doc.createElement("nombre_grupo");
				nombreGrupo.appendChild(doc.createTextNode(grupoNombre));
				grupo.appendChild(nombreGrupo);

				Element grupoCiclo = doc.createElement("ciclo");
				grupoCiclo.appendChild(doc.createTextNode(ciclo));
				grupo.appendChild(grupoCiclo);

				Element grupoCurso = doc.createElement("curso");
				grupoCurso.appendChild(doc.createTextNode(curso));
				grupo.appendChild(grupoCurso);

				// Agregar alumnos
				Element alumnos = doc.createElement("alumnos");
				do {
					if (resultSet.getInt("id_grupo") == grupoId) {
						Element alumno = doc.createElement("alumno");

						Element nia = doc.createElement("NIA");
						nia.appendChild(doc.createTextNode(String.valueOf(resultSet.getInt("NIA"))));
						alumno.appendChild(nia);

						Element nombre = doc.createElement("Nombre");
						nombre.appendChild(doc.createTextNode(resultSet.getString("Nombre")));
						alumno.appendChild(nombre);

						Element apellidos = doc.createElement("Apellidos");
						apellidos.appendChild(doc.createTextNode(resultSet.getString("Apellidos")));
						alumno.appendChild(apellidos);

						Element genero = doc.createElement("Genero");
						genero.appendChild(doc.createTextNode(resultSet.getString("Genero")));
						alumno.appendChild(genero);

						Element fechaNacimiento = doc.createElement("Fecha_de_nacimiento");
						fechaNacimiento
								.appendChild(doc.createTextNode(resultSet.getDate("Fecha_de_nacimiento").toString()));
						alumno.appendChild(fechaNacimiento);

						alumnos.appendChild(alumno);
					}
				} while (resultSet.next() && resultSet.getInt("id_grupo") == grupoId);

				grupo.appendChild(alumnos);
				root.appendChild(grupo);
			}

			// Guardar el documento XML
			try (FileOutputStream fileOutputStream = new FileOutputStream("grupos.xml")) {
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperties(new Properties() {
					{
						setProperty(OutputKeys.INDENT, "yes");
					}
				});
				transformer.transform(new DOMSource(doc), new StreamResult(fileOutputStream));
				System.out.println("Datos guardados en formato XML.");
			}
		} catch (Exception e) {
			System.out.println("Error al guardar los datos en XML: " + e.getMessage());
		}
	}

	private static void leerGruposDeXMLoJSONYGuardar(Connection connection, Scanner scanner) throws SQLException {
		System.out.println("Selecciona el formato de entrada (XML/JSON): ");
		String formato = scanner.nextLine();

		if (formato.equalsIgnoreCase("JSON")) {
			leerGruposDesdeJSON(connection);
		} else if (formato.equalsIgnoreCase("XML")) {
			leerGruposDesdeXML(connection);
		} else {
			System.out.println("Formato no reconocido.");
		}
	}

	private static void leerGruposDesdeXML(Connection connection) {
		try {
			File file = new File("grupos.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);

			NodeList grupos = doc.getElementsByTagName("grupo");

			for (int i = 0; i < grupos.getLength(); i++) {
				Element grupo = (Element) grupos.item(i);

				int id = Integer.parseInt(grupo.getAttribute("id"));
				String nombre = grupo.getAttribute("nombre");
				String ciclo = grupo.getAttribute("ciclo");
				String curso = grupo.getAttribute("curso");

				String grupoSql = "INSERT INTO grupo (ID, Nombre, Ciclo, Curso) VALUES (?, ?, ?, ?)";
				try (PreparedStatement grupoStmt = connection.prepareStatement(grupoSql)) {
					grupoStmt.setInt(1, id);
					grupoStmt.setString(2, nombre);
					grupoStmt.setString(3, ciclo);
					grupoStmt.setString(4, curso);
					grupoStmt.executeUpdate();
				}

				NodeList alumnos = grupo.getElementsByTagName("alumno");
				for (int j = 0; j < alumnos.getLength(); j++) {
					Element alumno = (Element) alumnos.item(j);
					int nia = Integer.parseInt(alumno.getAttribute("nia"));
					String nombreAlumno = alumno.getAttribute("nombre");
					String apellidos = alumno.getAttribute("apellidos");
					String genero = alumno.getAttribute("genero");
					String fechaNacimiento = alumno.getAttribute("fechaNacimiento");

					String alumnoSql = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_de_nacimiento, grupo_id) VALUES (?, ?, ?, ?, ?, ?)";
					try (PreparedStatement alumnoStmt = connection.prepareStatement(alumnoSql)) {
						alumnoStmt.setInt(1, nia);
						alumnoStmt.setString(2, nombreAlumno);
						alumnoStmt.setString(3, apellidos);
						alumnoStmt.setString(4, genero);
						alumnoStmt.setDate(5, Date.valueOf(fechaNacimiento));
						alumnoStmt.setInt(6, id);
						alumnoStmt.executeUpdate();
					}
				}
			}
			System.out.println("Grupos y alumnos leídos desde 'grupos.xml' y guardados en la base de datos.");
		} catch (Exception e) {
			System.out.println("Error al leer el archivo XML: " + e.getMessage());
		}
	}

	private static void leerGruposDesdeJSON(Connection connection) {
		try {
			File file = new File("grupos.json");
			String content = new String(Files.readAllBytes(file.toPath()));
			JSONArray grupos = new JSONArray(content);

			for (int i = 0; i < grupos.length(); i++) {
				JSONObject grupo = grupos.getJSONObject(i);

				int id = grupo.getInt("ID");
				String nombre = grupo.getString("Nombre");
				String ciclo = grupo.getString("Ciclo");
				String curso = grupo.getString("Curso");

				String grupoSql = "INSERT INTO grupo (ID, Nombre, Ciclo, Curso) VALUES (?, ?, ?, ?)";
				try (PreparedStatement grupoStmt = connection.prepareStatement(grupoSql)) {
					grupoStmt.setInt(1, id);
					grupoStmt.setString(2, nombre);
					grupoStmt.setString(3, ciclo);
					grupoStmt.setString(4, curso);
					grupoStmt.executeUpdate();
				}

				JSONArray alumnos = grupo.getJSONArray("Alumnos");
				for (int j = 0; j < alumnos.length(); j++) {
					JSONObject alumno = alumnos.getJSONObject(j);

					int nia = alumno.getInt("NIA");
					String nombreAlumno = alumno.getString("Nombre");
					String apellidos = alumno.getString("Apellidos");
					String genero = alumno.getString("Genero");
					String fechaNacimiento = alumno.getString("FechaNacimiento");

					String alumnoSql = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_de_nacimiento, grupo_id) VALUES (?, ?, ?, ?, ?, ?)";
					try (PreparedStatement alumnoStmt = connection.prepareStatement(alumnoSql)) {
						alumnoStmt.setInt(1, nia);
						alumnoStmt.setString(2, nombreAlumno);
						alumnoStmt.setString(3, apellidos);
						alumnoStmt.setString(4, genero);
						alumnoStmt.setDate(5, Date.valueOf(fechaNacimiento));
						alumnoStmt.setInt(6, id);
						alumnoStmt.executeUpdate();
					}
				}
			}
			System.out.println("Grupos y alumnos leídos desde 'grupos.json' y guardados en la base de datos.");
		} catch (Exception e) {
			System.out.println("Error al leer el archivo JSON: " + e.getMessage());
		}
	}

	private static void guardarGruposEnXMLoJSON(Connection connection, Scanner scanner) {
		System.out.println("Elige el formato de guardado: ");
		System.out.println("1. XML");
		System.out.println("2. JSON");
		int opcion = scanner.nextInt();
		scanner.nextLine(); // Consumir la nueva línea

		String sql = "SELECT g.id_grupo, g.nombre_grupo AS GrupoNombre, g.Ciclo, g.Curso, "
				+ "a.NIA, a.Nombre AS AlumnoNombre, a.Apellidos, a.Genero, a.Fecha_de_nacimiento "
				+ "FROM grupo g LEFT JOIN alumno a ON g.id_grupo = id_grupo ORDER BY g.id_grupo";

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			if (opcion == 1) {
				guardarGruposEnXML(resultSet);
			} else if (opcion == 2) {
				guardarGruposEnJSON(resultSet);
			} else {
				System.out.println("Opción inválida.");
			}
		} catch (Exception e) {
			System.out.println("Error al guardar los datos: " + e.getMessage());
		}
	}

	private static void guardarGruposEnXML(ResultSet resultSet) throws Exception {
	    try (PrintWriter writer = new PrintWriter("grupos.xml")) {
	        writer.println("<grupos>");
	        int currentGroupId = -1;

	        while (resultSet.next()) {
	            int groupId = resultSet.getInt("");

	            // Nuevo grupo
	            if (groupId != currentGroupId) {
	                if (currentGroupId != -1) {
	                    writer.println("</alumnos></grupo>");
	                }
	                currentGroupId = groupId;
	                writer.printf("<grupo id=\"%d\" nombre=\"%s\" ciclo=\"%s\" curso=\"%s\">%n", groupId,
	                        resultSet.getString("nombre_grupo"), resultSet.getString("ciclo"),
	                        resultSet.getString("curso"));
	                writer.println("<alumnos>");
	            }

	            // Alumno
	            if (resultSet.getString("Nombre") != null) {
	                writer.printf(
	                        "<alumno nia=\"%d\" nombre=\"%s\" apellidos=\"%s\" genero=\"%s\" fechaNacimiento=\"%s\"/>%n",
	                        resultSet.getInt("NIA"), resultSet.getString("Nombre"),
	                        resultSet.getString("Apellidos"), resultSet.getString("Genero"),
	                        resultSet.getDate("Fecha_de_nacimiento"));
	            }
	        }
	        writer.println("</alumnos></grupo></grupos>");
	        System.out.println("Datos guardados en 'grupos.xml'.");
	    }
	}
	private static void guardarGruposEnJSON(ResultSet resultSet) throws Exception {
		try (PrintWriter writer = new PrintWriter("grupos.json")) {
			writer.println("[");
			int currentGroupId = -1;
			boolean firstGroup = true;
			boolean firstStudent = true;

			while (resultSet.next()) {
				int groupId = resultSet.getInt("id_grupo");

				// Nuevo grupo
				if (groupId != currentGroupId) {
					if (!firstGroup) {
						writer.println("\t\t]"); // Cerrar la lista de alumnos
						writer.println("\t},");
					}
					firstGroup = false;
					firstStudent = true;
					currentGroupId = groupId;
					writer.printf(
							"\t{\"id_grupo\": %d, \"nombre_grupo\": \"%s\", \"ciclo\": \"%s\", \"curso\": \"%s\", \"alumnos\": [%n",
							groupId, resultSet.getString("nombre_grupo"), resultSet.getString("ciclo"),
							resultSet.getString("curso"));
				}

				// Alumno
				if (resultSet.getString("Nombre") != null) {
					if (!firstStudent) {
						writer.println(",");
					}
					firstStudent = false;
					writer.printf(
							"\t\t{\"NIA\": %d, \"Nombre\": \"%s\", \"Apellidos\": \"%s\", \"Genero\": \"%s\", \"FechaNacimiento\": \"%s\"}",
							resultSet.getInt("NIA"), resultSet.getString("Nombre"), resultSet.getString("Apellidos"),
							resultSet.getString("Genero"), resultSet.getDate("Fecha_de_nacimiento"));
				}
			}
			writer.println("\t\t]");
			writer.println("\t}");
			writer.println("]");
			System.out.println("Datos guardados en 'grupos.json'.");
		}
	}
}
