package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Usuario
 */
public class ConexionDB {

    private static Connection connection = null;

    private ConexionDB() {
    }

    public static Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document doc = builder.parse(ConexionDB.class.getClassLoader().getResourceAsStream("persistence.xml"));
                doc.getDocumentElement().normalize();

                String url = doc.getElementsByTagName("url").item(0).getTextContent();
                String user = doc.getElementsByTagName("user").item(0).getTextContent();
                String pass = doc.getElementsByTagName("password").item(0).getTextContent();
                String driver = doc.getElementsByTagName("driver").item(0).getTextContent();

                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Conexión establecida con éxito desde persistence.xml");

            } catch (Exception error) {
                System.err.println("Error al configurar la conexión: " + error.getMessage());
                throw error;
            }
        }
        return connection;
    }

    public static void cerrarConexion() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("?Conexión cerrada con éxito.");
                }
            } catch (SQLException error) {
                System.err.println("Error al cerrar la conexión: " + error.getMessage());
            }
        }
    }
}
