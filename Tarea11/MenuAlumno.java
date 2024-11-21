package Tarea11;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class MenuAlumno {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/Alumnos07";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "manager";
	private static List<Alumno> alumnosGuardados = new ArrayList<>();

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			while (true) {
				System.out.println("\nMenú de opciones:");
				System.out.println("1. Insertar un nuevo alumno");
				System.out.println("2. Mostrar todos los alumnos");
				System.out.println("3. Guardar alumnos en fichero (formato texto)");
				System.out.println("4. Leer alumnos de fichero y guardarlos en la BD");
				System.out.println("5. Modificar el nombre de un alumno por NIA");
				System.out.println("6. Eliminar un alumno por NIA");
				System.out.println("7. Eliminar alumnos por apellido");
				System.out.println("8. Guardar alumnos en fichero XML o JSON");
				System.out.println("9. Leer fichero XML o JSON y guardar en la BD");
				System.out.println("0. Salir");
				System.out.print("Selecciona una opción: ");
				int opcion = scanner.nextInt();
				scanner.nextLine(); // Consumir nueva línea

				switch (opcion) {
				case 1 -> insertarAlumno(scanner);
				case 2 -> mostrarAlumnos(connection);
				case 3 -> guardarEnFichero(connection, scanner);
				case 4 -> leerDeFicheroYGuardar(connection, scanner);
				case 5 -> modificarAlumno(connection, scanner);
				case 6 -> eliminarAlumnoPorNIA(connection, scanner);
				case 7 -> eliminarAlumnosPorApellido(connection, scanner);
				case 8 -> guardarEnXMLoJSON(connection, scanner);
				case 9 -> leerDeXMLoJSONYGuardar(connection, scanner);
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

	private static void insertarAlumno(Scanner scanner) {
		System.out.print("Introduce el NIA del alumno: ");
		int nia = scanner.nextInt();
		scanner.nextLine(); // Consumir nueva línea
		System.out.print("Introduce el nombre del alumno: ");
		String nombre = scanner.nextLine();
		System.out.print("Introduce los apellidos del alumno: ");
		String apellidos = scanner.nextLine();
		System.out.print("Introduce el género del alumno: ");
		String genero = scanner.nextLine();
		System.out.print("Introduce la fecha de nacimiento (YYYY-MM-DD): ");
		String fechaNacimiento = scanner.nextLine();
		System.out.print("Introduce el ciclo del alumno: ");
		String ciclo = scanner.nextLine();
		System.out.print("Introduce el curso del alumno: ");
		String curso = scanner.nextLine();
		System.out.print("Introduce el grupo del alumno: ");
		String grupo = scanner.nextLine();

		// Crear un objeto Alumno y agregarlo a la lista
		Alumno alumno = new Alumno(nia, nombre, apellidos, genero, Date.valueOf(fechaNacimiento), ciclo, curso, grupo);
		alumnosGuardados.add(alumno);
		System.out.println("Alumno guardado temporalmente.");
	}

	private static void mostrarAlumnos(Connection connection) throws SQLException {
		String sql = "SELECT * FROM alumno";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			System.out.println("Lista de alumnos:");
			while (resultSet.next()) {
				System.out.printf(
						"NIA: %d, Nombre: %s, Apellidos: %s, Género: %s, Fecha de nacimiento: %s, Ciclo: %s, Curso: %s, Grupo: %s%n",
						resultSet.getInt("NIA"), resultSet.getString("Nombre"), resultSet.getString("Apellidos"),
						resultSet.getString("Genero"), resultSet.getDate("Fecha_de_nacimiento"),
						resultSet.getString("Ciclo"), resultSet.getString("Curso"), resultSet.getString("Grupo"));
			}
		}
	}

	private static void guardarEnFichero(Connection connection, Scanner scanner) throws SQLException, IOException {
		System.out.print("Introduce el nombre del fichero: ");
		String filename = scanner.nextLine();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			String sql = "SELECT * FROM alumno";
			try (Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(sql)) {
				while (resultSet.next()) {
					writer.write(resultSet.getInt("NIA") + "," + resultSet.getString("Nombre") + ","
							+ resultSet.getString("Apellidos") + "," + resultSet.getString("Genero") + ","
							+ resultSet.getDate("Fecha_de_nacimiento") + "," + resultSet.getString("Ciclo") + ","
							+ resultSet.getString("Curso") + "," + resultSet.getString("Grupo"));
					writer.newLine();
				}
			}
		}
		System.out.println("Alumnos guardados en fichero correctamente.");
	}

	private static void leerDeFicheroYGuardar(Connection connection, Scanner scanner) throws SQLException {
		// SQL para verificar si el NIA ya existe
		String checkSQL = "SELECT COUNT(*) FROM alumno WHERE NIA = ?";
		// SQL para insertar un nuevo alumno
		String insertSQL = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_de_nacimiento, Ciclo, Curso, Grupo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		for (Alumno alumno : alumnosGuardados) {
			try (PreparedStatement checkStatement = connection.prepareStatement(checkSQL)) {
				// Verificar si el alumno ya existe por NIA
				checkStatement.setInt(1, alumno.nia);
				try (ResultSet resultSet = checkStatement.executeQuery()) {
					resultSet.next(); // Mover al primer (y único) resultado
					int count = resultSet.getInt(1);

					if (count > 0) {
						System.out.println("El alumno con NIA " + alumno.nia + " ya existe. No se insertará.");
					} else {
						// Si no existe, insertar el nuevo alumno
						try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
							insertStatement.setInt(1, alumno.nia);
							insertStatement.setString(2, alumno.nombre);
							insertStatement.setString(3, alumno.apellidos);
							insertStatement.setString(4, alumno.genero);
							insertStatement.setDate(5, alumno.fechaNacimiento);
							insertStatement.setString(6, alumno.ciclo);
							insertStatement.setString(7, alumno.curso);
							insertStatement.setString(8, alumno.grupo);
							insertStatement.executeUpdate();
							System.out.println("Alumno con NIA " + alumno.nia + " insertado en la base de datos.");
						}
					}
				}
			}
		}
	}

	private static void modificarAlumno(Connection connection, Scanner scanner) throws SQLException {
		System.out.print("Introduce el NIA del alumno a modificar: ");
		int nia = scanner.nextInt();
		scanner.nextLine(); // Consumir nueva línea
		System.out.print("Introduce el nuevo nombre: ");
		String nuevoNombre = scanner.nextLine();

		String sql = "UPDATE alumno SET Nombre = ? WHERE NIA = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, nuevoNombre);
			statement.setInt(2, nia);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Nombre del alumno actualizado correctamente.");
			} else {
				System.out.println("No se encontró un alumno con ese NIA.");
			}
		}
	}

	private static void eliminarAlumnoPorNIA(Connection connection, Scanner scanner) throws SQLException {
		System.out.print("Introduce el NIA del alumno a eliminar: ");
		int nia = scanner.nextInt();

		String sql = "DELETE FROM alumno WHERE NIA = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, nia);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Alumno eliminado correctamente.");
			} else {
				System.out.println("No se encontró un alumno con ese NIA.");
			}
		}
	}

	private static void eliminarAlumnosPorApellido(Connection connection, Scanner scanner) throws SQLException {
		System.out.print("Introduce una palabra contenida en los apellidos de los alumnos a eliminar: ");
		String palabra = scanner.nextLine();

		String sql = "DELETE FROM alumno WHERE Apellidos LIKE ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + palabra + "%");
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Se eliminaron " + rowsAffected + " alumnos que contenían la palabra \"" + palabra
						+ "\" en sus apellidos.");
			} else {
				System.out.println("No se encontró ningún alumno con esa condición.");
			}
		}
	}

	private static void guardarEnXMLoJSON(Connection connection, Scanner scanner) throws SQLException, IOException {
		System.out.print("Introduce el nombre del fichero (debe terminar en .xml o .json): ");
		String filename = scanner.nextLine();

		String sql = "SELECT * FROM alumno";
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

			if (filename.endsWith(".xml")) {
				writer.write("<alumnos>\n");
				while (resultSet.next()) {
					writer.write("  <alumno>\n");
					writer.write("    <NIA>" + resultSet.getInt("NIA") + "</NIA>\n");
					writer.write("    <Nombre>" + resultSet.getString("Nombre") + "</Nombre>\n");
					writer.write("    <Apellidos>" + resultSet.getString("Apellidos") + "</Apellidos>\n");
					writer.write("    <Genero>" + resultSet.getString("Genero") + "</Genero>\n");
					writer.write("    <Fecha_de_nacimiento>" + resultSet.getDate("Fecha_de_nacimiento")
							+ "</Fecha_de_nacimiento>\n");
					writer.write("    <Ciclo>" + resultSet.getString("Ciclo") + "</Ciclo>\n");
					writer.write("    <Curso>" + resultSet.getString("Curso") + "</Curso>\n");
					writer.write("    <Grupo>" + resultSet.getString("Grupo") + "</Grupo>\n");
					writer.write("  </alumno>\n");
				}
				writer.write("</alumnos>");
			} else if (filename.endsWith(".json")) {
				writer.write("[\n");
				boolean first = true;
				while (resultSet.next()) {
					if (!first)
						writer.write(",\n");
					writer.write("  {\n");
					writer.write("    \"NIA\": " + resultSet.getInt("NIA") + ",\n");
					writer.write("    \"Nombre\": \"" + resultSet.getString("Nombre") + "\",\n");
					writer.write("    \"Apellidos\": \"" + resultSet.getString("Apellidos") + "\",\n");
					writer.write("    \"Genero\": \"" + resultSet.getString("Genero") + "\",\n");
					writer.write(
							"    \"Fecha_de_nacimiento\": \"" + resultSet.getDate("Fecha_de_nacimiento") + "\",\n");
					writer.write("    \"Ciclo\": \"" + resultSet.getString("Ciclo") + "\",\n");
					writer.write("    \"Curso\": \"" + resultSet.getString("Curso") + "\",\n");
					writer.write("    \"Grupo\": \"" + resultSet.getString("Grupo") + "\"\n");
					writer.write("  }");
					first = false;
				}
				writer.write("\n]");
			} else {
				System.out.println("El archivo debe terminar en .xml o .json.");
				return;
			}

			System.out.println("Alumnos guardados en el fichero correctamente.");
		}
	}

	private static void leerDeXMLoJSONYGuardar(Connection connection, Scanner scanner)
			throws SQLException, IOException {
		System.out.print("Introduce el nombre del fichero (debe terminar en .xml o .json): ");
		String filename = scanner.nextLine();

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String sql = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_de_nacimiento, Ciclo, Curso, Grupo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			if (filename.endsWith(".xml")) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.contains("<alumno>")) {
						int nia = Integer.parseInt(reader.readLine().replaceAll("<.*?>", "").trim());
						String nombre = reader.readLine().replaceAll("<.*?>", "").trim();
						String apellidos = reader.readLine().replaceAll("<.*?>", "").trim();
						String genero = reader.readLine().replaceAll("<.*?>", "").trim();
						Date fechaNacimiento = Date.valueOf(reader.readLine().replaceAll("<.*?>", "").trim());
						String ciclo = reader.readLine().replaceAll("<.*?>", "").trim();
						String curso = reader.readLine().replaceAll("<.*?>", "").trim();
						String grupo = reader.readLine().replaceAll("<.*?>", "").trim();
						reader.readLine(); // Salta </alumno>

						try (PreparedStatement statement = connection.prepareStatement(sql)) {
							statement.setInt(1, nia);
							statement.setString(2, nombre);
							statement.setString(3, apellidos);
							statement.setString(4, genero);
							statement.setDate(5, fechaNacimiento);
							statement.setString(6, ciclo);
							statement.setString(7, curso);
							statement.setString(8, grupo);
							statement.executeUpdate();
						}
					}
				}
			} else if (filename.endsWith(".json")) {
				StringBuilder jsonContent = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					jsonContent.append(line);
				}

				// Usa una biblioteca JSON como org.json o Jackson para parsear el contenido
				// JSON.
				// Aquí se muestra un ejemplo básico (puedes ajustarlo según la biblioteca que
				// prefieras).
				JSONArray alumnos = new JSONArray(jsonContent.toString());
				for (int i = 0; i < alumnos.length(); i++) {
					JSONObject alumno = alumnos.getJSONObject(i);
					try (PreparedStatement statement = connection.prepareStatement(sql)) {
						statement.setInt(1, alumno.getInt("NIA"));
						statement.setString(2, alumno.getString("Nombre"));
						statement.setString(3, alumno.getString("Apellidos"));
						statement.setString(4, alumno.getString("Genero"));
						statement.setDate(5, Date.valueOf(alumno.getString("Fecha_de_nacimiento")));
						statement.setString(6, alumno.getString("Ciclo"));
						statement.setString(7, alumno.getString("Curso"));
						statement.setString(8, alumno.getString("Grupo"));
						statement.executeUpdate();
					}
				}
			} else {
				System.out.println("El archivo debe terminar en .xml o .json.");
			}

			System.out.println("Datos leídos del fichero y guardados en la BD correctamente.");
		}
	}

}
