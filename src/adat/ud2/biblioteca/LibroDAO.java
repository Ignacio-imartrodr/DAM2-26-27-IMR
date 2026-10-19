package adat.ud2.biblioteca;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroDAO {
    public static List<LibroDTO> findAll() {
        List<LibroDTO> alumnos = null;
        Connection con = ConexionBD.conectar();

        try {
            Statement sentenciaSQL = con.createStatement();
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM libros");
            alumnos = new ArrayList<>();
            while (rs.next()) {
                alumnos.add(new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion")));
            }
            rs.close();
            sentenciaSQL.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    public static boolean insertar(LibroDTO libro) {

        Connection con = ConexionBD.conectar();
        String sql = """
                INSERT INTO libros
                VALUES
                (?, ?, ?, ?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(++parIndx, libro.getId());
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPublicación()));
            sentenciaSQL.setDouble(++parIndx, libro.getValoracion());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean borrar(int id) {
        Connection con = ConexionBD.conectar();
        String sql = """
                DELETE FROM libros
                WHERE ID = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(1, id);
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean borrar(LibroDTO alumno) {
        Connection con = ConexionBD.conectar();
        String sql = """
                DELETE FROM libros
                WHERE ID = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(1, alumno.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static LibroDTO find(LibroDTO alumno) {
        LibroDTO alumnoRes = null;
        Connection con = ConexionBD.conectar();
        String sql = """
                SELECT * FROM libros
                WHERE ID = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(1, alumno.getId());
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                alumnoRes = new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion"));
            }
            rs.close();
            sentenciaSQL.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnoRes;

    }


    public static boolean actualizar(LibroDTO libro) {
        Connection con = ConexionBD.conectar();
        String sql = """
                UPDATE libros
                SET titulo = ?,
                    fecha_publicacion = ?,
                    valoracion = ?
                WHERE ID = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setString(1, libro.getTitulo());
            sentenciaSQL.setDate(2, Date.valueOf(libro.getFechaPublicación()));
            sentenciaSQL.setDouble(3, libro.getValoracion());
            sentenciaSQL.setInt(4, libro.getId());            
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    /*
     * MÉTODOS SIN PATRÓN DTO
     */
    public static boolean insertar(int id, String titulo, String apellidos, LocalDate fechaNacimiento, String curso,
            Double notaMedia) {

        Connection con = ConexionBD.conectar();
        String sql = """
                INSERT INTO libros
                VALUES
                (?, ?, ?, ?)
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            int parIndx = 0;
            sentenciaSQL.setInt(++parIndx, id);
            sentenciaSQL.setString(++parIndx, titulo);
            sentenciaSQL.setDate(++parIndx, Date.valueOf(fechaNacimiento));
            sentenciaSQL.setDouble(++parIndx, notaMedia);
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void findAllOld() {
        Connection con = ConexionBD.conectar();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM libros");
            System.out.println("\nLIBROS");
            System.out.println("=======\n");
            while (rs.next()) {
                System.out.println("Id: " + rs.getInt("id"));
                System.out.println("Título: " + rs.getString("titulo"));
                System.out.println("Fecha de publicación: " + rs.getDate("fecha_publicacion"));
                System.out.println("Valoración media: " + rs.getDouble("valoracion"));
                System.out.println("\n");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
