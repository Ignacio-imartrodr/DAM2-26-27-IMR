package adat.ud2.bibliotecaV1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection conectar() {
        final String URL = "jdbc:h2:./BDs/Biblioteca/biliotecaV1";
        final String USER = "sa";
        final String PASS = "";

        Connection con = null;

        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return con;
    }
}
