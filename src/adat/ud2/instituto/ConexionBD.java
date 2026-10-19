package adat.ud2.instituto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection conectar() {
        String url = "jdbc:h2:./././BD/instituto";
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
