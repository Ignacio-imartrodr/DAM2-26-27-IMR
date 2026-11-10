package adat.ud2.examen.corregido.conexion;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static final String CONFIG_FILE = "BDs\\Torneo3x3\\configSQLite.properties";

    public static Connection getConexion() throws SQLException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer " + CONFIG_FILE, e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password", "");

        return DriverManager.getConnection(url, user, pass);
    }

}
