package adat.ud2.bibliotecaV2;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibroDAOtran extends AbstractDAOtran<LibroDTO>{

    LibroDAOtran(Connection con){
        if (con == null) {
            throw new IllegalArgumentException("La conexion no debe ser nula");
        }
        this.con = con;
    }

    @Override 
    public boolean insertar(LibroDTO libro) {
        
        String sql = """
                INSERT INTO libros
                (titulo, autor, fecha_publicacion, valoracion, id_usuario)
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
            UsuarioDTO us = libro.getUsuario();
            if (us != null) {
                sentenciaSQL.setInt(++parIndx, us.getId());  
            } else {
                sentenciaSQL.setNull(++parIndx, java.sql.Types.NULL);     
            }
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
            if (rs.next()) {
                libros = new ArrayList<>();
                do {
                    int idUsuario = rs.getInt("id_usuario");
                    if (rs.wasNull()) {
                        idUsuario = -1;
                    }
                    libros.add(new LibroDTO(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getDate("fecha_publicacion").toLocalDate(),
                            rs.getDouble("valoracion"),
                            new UsuarioDAOtran(con).findById(idUsuario)
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
                int idUsuario = rs.getInt("id_usuario");
                if (rs.wasNull()) {
                    idUsuario = -1;
                }
                libroRes = new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion"),
                        new UsuarioDAOtran(con).findById(idUsuario)
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libroRes;
    }
    
    public LibroDTO[] findByPoseedor(UsuarioDTO us) {
        if (us == null || us.getId() < 0) {
            return null;
        }
        List<LibroDTO> listRes = null;
        String sql = """
                SELECT * FROM libros
                WHERE id_usuario = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, us.getId());
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
                            us
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
                    id_usuario = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            int parIndx = 0;
            sentenciaSQL.setString(++parIndx, libro.getTitulo());
            sentenciaSQL.setString(++parIndx, libro.getAutor());
            sentenciaSQL.setDate(++parIndx, Date.valueOf(libro.getFechaPub()));
            sentenciaSQL.setDouble(++parIndx, libro.getValoracion());
            UsuarioDTO us = libro.getUsuario();
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

    public boolean actualizarUsuario(LibroDTO libro) {
        if (libro == null || libro.getId() < 0) {
            return false;
        }
        String sql = """
                UPDATE libros
                SET id_usuario = ?
                WHERE id = ?
                """;
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            int parIndx = 0;
            UsuarioDTO us = libro.getUsuario();
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
}
