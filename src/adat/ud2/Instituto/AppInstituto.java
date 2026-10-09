package adat.ud2.Instituto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AppInstituto {
    public static void main(String[] args) {
        String url = "jdbc:h2:mem:instituto;DB_CLOSE_DELAY=-1";
        String user = "sa";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement()) {
            
            // Crear una tabla de alumno
            String sql = """
                    CREATE TABLE alumnos(
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nombre VARCHAR (50),
                        apellidos VARCHAR (100),
                        fecha_nacimiento DATE,
                        curso VARCHAR (100),
                        nota_media DECIMAL (4,2)
                    );
                    """;
            stmt.execute(sql);

            // Insertar datos
            stmt.execute("INSERT INTO alumnos VALUES(DEFAULT,'Pepe', 'Perez Gonzalez', '2007-8-9', 'DAM2', 5.5)");
            stmt.execute("INSERT INTO alumnos VALUES(DEFAULT, 'Juan', 'Perez Gonzalez', '2007-8-9', 'DAM2', 6.1)");
            stmt.execute("INSERT INTO alumnos VALUES(DEFAULT, 'Marta', 'Perez Gonzalez', '2007-8-9', 'DAM2', 7)");
            stmt.execute("INSERT INTO alumnos VALUES(DEFAULT, 'Josue', 'Perez Gonzalez', '2007-8-9', 'DAM2', 4.9)");
            stmt.execute("INSERT INTO alumnos VALUES(DEFAULT, 'Antonio', 'Perez Gonzalez', '2007-8-9', 'DAM2', 8.7)");

            // Recuperar Listado de alumnos
            System.out.println("\nLISTADO DE ALUMNOS:");
            sql = "SELECT * FROM alumnos";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String apellidos = rs.getString(3);
                Date fechaNacimiento = rs.getDate(4);
                String curso = rs.getString(5);
                Double notaMedia = rs.getDouble(6);
                System.out.println("Alumno " + id + ": " + name + " " + apellidos + ", nacido = " + fechaNacimiento + ", curso = " + curso + ", nota media = " + notaMedia);
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("\nIndica el id del alumno a recuperar: ");
            /* Ejemplos de entradas para SQL Injection
               "1 OR 1=1" para obtener todos los registros
               "1; delete from alumnos" para ejecutar otra consulta que borre todos los alumnos
             */ 
            String idR = sc.nextLine();
            sc.close();

            sql = "SELECT * FROM alumnos WHERE id = " + idR;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String apellidos = rs.getString(3);
                Date fechaNacimiento = rs.getDate(4);
                String curso = rs.getString(5);
                Double notaMedia = rs.getDouble(6);
                System.out.println("Alumno " + id + ": " + name + " " + apellidos + ", nacido = " + fechaNacimiento + ", curso = " + curso + ", nota media = " + notaMedia);
            }

            // Recuperar Listado de alumnos
            System.out.println("\nLISTADO DE ALUMNOS:");
            sql = "SELECT * FROM alumnos";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String apellidos = rs.getString(3);
                Date fechaNacimiento = rs.getDate(4);
                String curso = rs.getString(5);
                Double notaMedia = rs.getDouble(6);
                System.out.println("Alumno " + id + ": " + name + " " + apellidos + ", nacido = " + fechaNacimiento + ", curso = " + curso + ", nota media = " + notaMedia);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
