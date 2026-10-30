package adat.ud2.h2bd.biblioteca.daoYdto.libro;

import java.util.ArrayList;
import java.util.List;

import adat.ud2.h2bd.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.h2bd.biblioteca.daoYdto.genero.GeneroDAOtran;
import adat.ud2.h2bd.biblioteca.daoYdto.genero.GeneroDTO;
import adat.ud2.h2bd.biblioteca.daoYdto.prestamos.PrestamoDAOtran;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LibroDAOtran extends AbstractDAOtran<LibroDTO>{

    public LibroDAOtran(Connection con){
        super(con);
    }

    @Override 
    public int insertar(LibroDTO libro) {
        if (libro.getCopiasDisponibles() < 0 || libro.getCopiasTotales() < 1) {
            return -1;
        }

        String sql = "INSERT INTO " + getTablaName() + "(titulo, autor, fecha_publicacion, valoracion, id_genero, copias_totales, copias_disponibles) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
            if (sentenciaSQL.executeUpdate() <= 0) {
                return -1;
            }
            try (ResultSet claves = sentenciaSQL.getGeneratedKeys()) {
                return claves.next() ? claves.getInt(1) : -1;
            }
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println("Ya existe un " + getTablaName().substring(0, getTablaName().length() - 1) + " con la convinación de título, autor y fecha de publicación de\"" + libro.toString() + "\"");
            } else {
                e.printStackTrace();
            }
            return -1;
        }
    }

    public LibroDTO[] findByGenero(GeneroDTO gen) {
        if (gen == null || gen.getId() < 0) {
            return null;
        }
        List<LibroDTO> listRes = null;
        String sql = "SELECT * FROM " + getTablaName() + " WHERE id_genero = ?";
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, gen.getId());
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                listRes = new ArrayList<>();
                do {
                    listRes.add(new LibroDTO(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getDate("fecha_publicacion").toLocalDate(),
                            rs.getDouble("valoracion"),
                            gen,
                            rs.getInt("copias_totales"),
                            rs.getInt("copias_disponibles")
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
        String sql = "UPDATE " + getTablaName() + " SET titulo = ?, autor = ?, fecha_publicacion = ?, valoracion = ?, id_genero = ?, copias_totales = ?, copias_disponibles = ? WHERE id = ?";
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
        String sql = "UPDATE " + getTablaName() + " SET id_genero = ? WHERE id = ?";
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
        String sql = "UPDATE " + getTablaName() + " SET copias_totales = ?, copias_disponibles = ? WHERE id = ?";
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

    @Override
    public String getTablaName() {
        return "libros";
    }

    @Override
    public LibroDTO[] parseRs(ResultSet rs) {
        List<LibroDTO> libros = null;
        try {
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
                            rs.getInt("copias_disponibles")
                        ));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se pudo acceder a la BD");
        }
        return libros == null ? null : libros.toArray(new LibroDTO[0]);
    }

    @Override
    public boolean isBorradoPosible(LibroDTO dto) {
        if (dto == null || new PrestamoDAOtran(con).findByLibro(dto) != null) {
            return false;
        }
        return true;
    }
}
