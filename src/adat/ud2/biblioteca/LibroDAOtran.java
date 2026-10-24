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

    public boolean beginTransaction(){
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
    public boolean endTransaction(boolean hasSucceded){
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

        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
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

    public boolean insertar(UsuarioDTO us) {
        String sql = """
                INSERT INTO usuarios
                (nombre)
                VALUES
                (?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            //sentenciaSQL.setInt(++parIndx, libro.getId());
            sentenciaSQL.setString(++parIndx, us.getNombre());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean borrar(LibroDTO libro) {
        return borrarLibro(libro.getId());
    }

    public boolean borrar(UsuarioDTO us) {
        return borrarUsuario(us.getId());
    }

    public boolean borrarLibro(int id) {
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

    public boolean borrarUsuario(int id) {
        String sql = """
                DELETE FROM usuarios
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

    public List<LibroDTO> findAllLibros() {
        List<LibroDTO> libros = null;

        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM libros");
            libros = new ArrayList<>();
            while (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                if (rs.wasNull()) {
                    idUsuario = -1;
                }
                libros.add(new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion"),
                        findUsuario(idUsuario)
                    ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public List<UsuarioDTO> findAllUsuarios() {
        List<UsuarioDTO> usuarios = null;

        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM usuarios");
            usuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios.add(new UsuarioDTO(
                        rs.getInt("id"),
                        rs.getString("nombre")
                    ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public LibroDTO find(LibroDTO libro) {
        return findLibro(libro.getId());
    }

    public UsuarioDTO find(UsuarioDTO us) {
        return findUsuario(us.getId());
    }

    public LibroDTO findLibro(int id) {
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
                int idUsuario = rs.getInt("idUsuario");
                if (rs.wasNull()) {
                    idUsuario = -1;
                }
                LibroRes = new LibroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getDate("fecha_publicacion").toLocalDate(),
                        rs.getDouble("valoracion"),
                        findUsuario(idUsuario)
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LibroRes;
    }

    public UsuarioDTO findUsuario(int id) {
        if (id < 0) {
            return null;
        }
        UsuarioDTO usRes = null;
        String sql = """
                SELECT * FROM usuarios
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                usRes = new UsuarioDTO(
                        rs.getInt("id"),
                        rs.getString("nombre")
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usRes;
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
