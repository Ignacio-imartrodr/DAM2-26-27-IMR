package adat.ud2.biblioteca;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroDAOtran {
    private Connection con;

    LibroDAOtran(Connection con){
        if (con == null) {
            throw new IllegalArgumentException("La conexion no debe ser nula");
        }
        this.con = con;
    }

    public List<LibroDTO> findAll() {
        List<LibroDTO> libros = null;

        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM libros");
            libros = new ArrayList<>();
            while (rs.next()) {
                libros.add(new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public boolean insertar(LibroDTO libro) {
        String sql = """
                INSERT INTO libros
                (titulo, autor, fecha_publicacion, valoracion)
                VALUES
                (?, ?, ?, ?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            //sentenciaSQL.setInt(++parIndx, libro.getId());
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setString(++parIndx, libro.getAutor());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPub()));
            sentenciaSQL.setDouble(++parIndx, libro.getValoracion());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean borrar(LibroDTO libro) {
        return borrar(libro.getId());
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

    public LibroDTO find(LibroDTO libro) {
        return find(libro.getId());
    }

    public LibroDTO find(int id) {
        if (id < 0) {
            return null;
        }
        LibroDTO LibroRes = null;
        String sql = """
                SELECT * FROM libros
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                LibroRes = new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LibroRes;
    }

    public boolean actualizar(LibroDTO libro) {
        String sql = """
                UPDATE libros
                SET titulo = ?,
                    autor = ?,
                    fecha_publicacion = ?,
                    valoracion = ?
                WHERE ID = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            int parIndx = 0;
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setString(++parIndx, libro.getAutor());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPub()));
            sentenciaSQL.setDouble(++parIndx, libro.getValoracion());
            sentenciaSQL.setInt(++parIndx, libro.getId());            
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
