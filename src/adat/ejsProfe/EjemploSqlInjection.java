package adat.ejsProfe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EjemploSqlInjection {

    public static void main(String[] args) {
        // URL de conexión para H2 en memoria
        // DB_CLOSE_DELAY=-1 mantiene la BD activa mientras la JVM esté viva
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String user = "sa";
        String password = "";

        // Conexión con try-resources. Se cierra automáticamente
        try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement()) {

            // Crear una tabla de ejemplo
            stmt.execute("CREATE TABLE users(id INT PRIMARY KEY, name VARCHAR(50))");
            System.out.println("✅ Tabla creada con éxito.");

            // Insertar datos
            stmt.execute("INSERT INTO users VALUES(1, 'Pepe')");
            stmt.execute("INSERT INTO users VALUES(2, 'Marta')");
            System.out.println("✅ Datos insertados.");

            // Recuperar Listado de usuarios
            System.out.println("\nLISTADO DE USUARIOS:");
            String sql = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println("Usuario " + id + ": " + name);
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("\nIndica el id del usuario a recuperar: ");
            /* Ejemplos de entradas para SQL Injection
               "1 OR 1=1" para obtener todos los registros
               "1; delete from users" para ejecutar otra consulta que borre todos los usuarios
             */ 
            String idR = sc.nextLine();
            sc.close();

            sql = "SELECT * FROM users WHERE id = " + idR;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println("Usuario " + id + ": " + name);
            }

            // Recuperar Listado de usuarios
            System.out.println("\nLISTADO DE USUARIOS:");
            sql = "SELECT * FROM users";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println("Usuario " + id + ": " + name);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
