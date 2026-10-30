package adat.ud2.sqlLite.bdColaborativa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * @author Adrián Gómez
 */
public class JbdcUtil {

    private static final String RUTA_PROPERTIES = "./BD/properties";

    public static Connection getConnection() {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(RUTA_PROPERTIES)) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero properties: " + e.getMessage());
            return null;
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password", "");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error de sql: " + e.getMessage());
            return null;
        }
    }
}