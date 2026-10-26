package adat.ud2.biblioteca.daoYdto.libro;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.biblioteca.daoYdto.genero.GeneroDAOtran;
import adat.ud2.biblioteca.daoYdto.genero.GeneroDTO;

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
                (titulo, autor, fecha_publicacion, valoracion, id_genero, copias_totales, copias_disponibles)
                VALUES
                (?, ?, ?, ?, ?, ?, ?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setString(++parIndx, libro.getAutor());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPub()));
            Double valoracion = libro.getValoracion();
            if (valoracion != null) {
                sentenciaSQL.setDouble(++parIndx, valoracion);
            } else {
                sentenciaSQL.setNull(++parIndx, java.sql.Types.NULL);
            }
            GeneroDTO gen = libro.getGenero();
            if (gen != null) {
                sentenciaSQL.setInt(++parIndx, gen.getId());  
            } else {
                sentenciaSQL.setNull(++parIndx, java.sql.Types.NULL);     
            }
            sentenciaSQL.setInt(++parIndx, libro.getCopiasTotales());
            sentenciaSQL.setInt(++parIndx, libro.getCopiasDisponibles());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override 
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
            if (rs.next()) {
                libros = new ArrayList<>();
                do {
                    int idGenero = rs.getInt("id_genero");
                    if (rs.wasNull()) {
                        idGenero = -1;
                    }
                    libros.add(new LibroDTO(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getDate("fecha_publicacion").toLocalDate(),
                            rs.getDouble("valoracion"),
                            new GeneroDAOtran(con).findById(idGenero),
                            rs.getInt("copias_totales"),
                            rs.getInt("copias_disponbles")
                        ));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros == null ? null : libros.toArray(new LibroDTO[0]);
    }

    @Override
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
                int idGenero = rs.getInt("id_genero");
                if (rs.wasNull()) {
                    idGenero = -1;
                }
                libroRes = new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion"),
                        new GeneroDAOtran(con).findById(idGenero),
                        rs.getInt("copias_totales"),
                        rs.getInt("copias_disponbles")
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libroRes;
    }
    
    public LibroDTO[] findByGenero(GeneroDTO gen) {
        if (gen == null || gen.getId() < 0) {
            return null;
        }
        List<LibroDTO> listRes = null;
        String sql = """
                SELECT * FROM libros
                WHERE id_genero = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, gen.getId());
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                listRes = new ArrayList<>(rs.getFetchSize());
                do {
                    listRes.add(new LibroDTO(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getDate("fecha_publicacion").toLocalDate(),
                            rs.getDouble("valoracion"),
                            gen,
                            rs.getInt("copias_totales"),
                            rs.getInt("copias_disponbles")
                    ));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRes == null ? null : listRes.toArray(new LibroDTO[0]);
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
                    id_genero = ?,
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
            Double valoracion = libro.getValoracion();
            if (valoracion != null) {
                sentenciaSQL.setDouble(++parIndx, valoracion);
            } else {
                sentenciaSQL.setNull(++parIndx, java.sql.Types.NULL);
            }
            GeneroDTO us = libro.getGenero();
            if (us != null) {
                sentenciaSQL.setInt(++parIndx, us.getId());  
            } else {
                sentenciaSQL.setNull(++parIndx, java.sql.Types.NULL);     
            }
            sentenciaSQL.setInt(++parIndx, libro.getCopiasTotales());
            sentenciaSQL.setInt(++parIndx, libro.getCopiasDisponibles());
            sentenciaSQL.setInt(++parIndx, libro.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarGenero(LibroDTO libro) {
        if (libro == null || libro.getId() < 0) {
            return false;
        }
        String sql = """
                UPDATE libros
                SET id_genero = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            int parIndx = 0;
            GeneroDTO us = libro.getGenero();
            if (us != null) {
                sentenciaSQL.setInt(++parIndx, us.getId());  
            } else {
                sentenciaSQL.setNull(++parIndx, java.sql.Types.NULL);     
            }
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
