package adat.ud2.h2bd.oldBiblioteca;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroDAOtranOld {
    private Connection con;

    LibroDAOtranOld(Connection con){
        if (con == null) {
            throw new IllegalArgumentException("La conexion no debe ser nula");
        }
        this.con = con;
    }

    public List<LibroDTOold> findAll() {
        List<LibroDTOold> libros = null;

        try {
            Statement sentenciaSQL = con.createStatement();
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM libros");
            libros = new ArrayList<>();
            while (rs.next()) {
                libros.add(new LibroDTOold(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion")));
            }
            rs.close();
            sentenciaSQL.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public boolean insertar(LibroDTOold libro) {
        String sql = """
                INSERT INTO libros
                VALUES
                (DEFAULT, ?, ?, ?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            //sentenciaSQL.setInt(++parIndx, libro.getId());
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPublicación()));
            sentenciaSQL.setDouble(++parIndx, libro.getValoracion());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean borrar(int id) {
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

    public boolean borrar(LibroDTOold alumno) {
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

    public LibroDTOold find(LibroDTOold libro) {
        return find(libro.getId());
    }

    public LibroDTOold find(int id) {
        if (id < 0) {
            return null;
        }
        LibroDTOold LibroRes = null;
        String sql = """
                SELECT * FROM libros
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                LibroRes = new LibroDTOold(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LibroRes;
    }

    public boolean actualizar(LibroDTOold libro) {
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
}
