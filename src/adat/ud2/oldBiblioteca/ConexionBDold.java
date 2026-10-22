package adat.ud2.oldBiblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDold {
    public static Connection conectar() {
        final String URL = "jdbc:h2:./././BD/bibliotecaOld";
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
