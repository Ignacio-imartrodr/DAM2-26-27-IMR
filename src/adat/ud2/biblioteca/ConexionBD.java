package adat.ud2.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection conectar() {
        final String URL = "jdbc:h2:file:./BDs/Biblioteca/bilioteca";
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
    public static boolean beginTransaction(Connection con) {
        boolean isSuccess = false;
        try {
            if (!con.getAutoCommit()) {
                System.err.println("Transacción ya en curso");
            } else {
                con.setAutoCommit(false);
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static boolean endTransaction(Connection con, boolean hasSucceded){
        boolean isSuccess = false;
        
        try {
            if (con.getAutoCommit()) {
                System.err.println("No hay ninguna transacción ya en curso");
                return isSuccess;
            }
            if (hasSucceded) {
                con.commit();
            } else {
                con.rollback();
            }
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }
}
