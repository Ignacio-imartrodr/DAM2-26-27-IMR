package adat.ud2.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection conectar() {
        String url = "jdbc:h2:./././BD/biblioteca";
        String user = "sa";
        String pass = "";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return con;
    }

}
