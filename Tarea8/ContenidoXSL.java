package Tarea8;

public class ContenidoXSL {
	public static String obtenerContenidoXSL() {
		return """
				    <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
				        <xsl:template match="/">
				            <html>
				            <head>
				                <title>Lista de Estudiantes</title>
				                <style>
				                    table { width: 100%; border-collapse: collapse; }
				                    th, td { border: 1px solid black; padding: 8px; text-align: center; }
				                    th { background-color: #f2f2f2; }
				                </style>
				            </head>
				            <body>
				                <h2>Tabla de Estudiantes</h2>
				                <table>
				                    <tr>
				                        <th>NIA</th>
				                        <th>Nombre</th>
				                        <th>Apellidos</th>
				                        <th>Género</th>
				                        <th>Fecha de Nacimiento</th>
				                        <th>Ciclo</th>
				                        <th>Curso</th>
				                        <th>Grupo</th>
				                    </tr>
				                    <!-- Recorre cada elemento Alumno en el XML -->
				                    <xsl:for-each select="Alumnos/Alumno">
				                        <tr>
				                            <td><xsl:value-of select="@NIA"/></td>
				                            <td><xsl:value-of select="Nombre"/></td>
				                            <td><xsl:value-of select="Apellidos"/></td>
				                            <td><xsl:value-of select="Genero"/></td>
				                            <td><xsl:value-of select="FechaNacimiento"/></td>
				                            <td><xsl:value-of select="Ciclo"/></td>
				                            <td><xsl:value-of select="Ciclo/@curso"/></td>
				                            <td><xsl:value-of select="Ciclo/@Grupo"/></td>
				                        </tr>
				                    </xsl:for-each>
				                </table>
				            </body>
				            </html>
				        </xsl:template>
				    </xsl:stylesheet>
				""";
	}
}
