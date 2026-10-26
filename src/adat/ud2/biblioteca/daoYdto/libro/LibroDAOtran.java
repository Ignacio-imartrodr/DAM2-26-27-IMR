package adat.ud2.biblioteca.daoYdto.libro;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroDAOtran extends AbstractDAOtran<LibroDTO>{

    public LibroDAOtran(Connection con){
        super(con);
    }

    @Override 
    public boolean insertar(LibroDTO libro) {
        if (libro.getCopiasDisponibles() < 0 || libro.getCopiasTotales() < 1) {
            return false;
        }

        String sql = """
                INSERT INTO libros
                (titulo, autor, fecha_publicacion, valoracion, copias_totales, copias_disponibles)
                VALUES
                (?, ?, ?, ?, ?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            //sentenciaSQL.setInt(++parIndx, libro.getId());
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setString(++parIndx, libro.getAutor());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPub()));
            sentenciaSQL.setDouble(++parIndx, libro.getValoracion());
            sentenciaSQL.setInt(++parIndx, libro.getCopiasTotales());
            sentenciaSQL.setInt(++parIndx, libro.getCopiasDisponibles());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override 
    public boolean borrar(LibroDTO libro) {
        if (libro == null) {
            return false;
        }
        return borrarById(libro.getId());
    }

    public boolean borrarById(int id) {
        if (id < 0) {
            return false;
        }
        String sql = """
                DELETE FROM libros
                WHERE id = ?
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

    @Override 
    public LibroDTO[] findAll() {
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
                        rs.getDouble("valoracion")
                    ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros == null ? null : libros.toArray(new LibroDTO[0]);
    }

    @Override
    public LibroDTO find(LibroDTO libro) {
        if (libro == null) {
            return null;
        }
        return findById(libro.getId());
    }

    public LibroDTO findById(int id) {
        if (id < 0) {
            return null;
        }
        LibroDTO libroRes = null;
        String sql = """
                SELECT * FROM libros
                WHERE id = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                libroRes = new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion")
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libroRes;
    }

    @Override
    public boolean actualizar(LibroDTO libro) {
        if (libro == null || libro.getId() < 0) {
            return false;
        }
        String sql = """
                UPDATE libros
                SET titulo = ?,
                    autor = ?,
                    fecha_publicacion = ?,
                    valoracion = ?,
                    copias_totales = ?,
                    copias_disponibles = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            int parIndx = 0;
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setString(++parIndx, libro.getAutor());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPub()));
            sentenciaSQL.setDouble(++parIndx, libro.getValoracion());
            sentenciaSQL.setInt(++parIndx, libro.getCopiasTotales());
            sentenciaSQL.setInt(++parIndx, libro.getCopiasDisponibles());
            sentenciaSQL.setInt(++parIndx, libro.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCopias(LibroDTO libro) {
        if (libro == null || libro.getId() < 0) {
            return false;
        }
        String sql = """
                UPDATE libros
                SET copias_totales = ?,
                    copias_disponibles = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            int parIndx = 0;
            sentenciaSQL.setInt(++parIndx, libro.getCopiasTotales());
            sentenciaSQL.setInt(++parIndx, libro.getCopiasDisponibles());
            sentenciaSQL.setInt(++parIndx, libro.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
