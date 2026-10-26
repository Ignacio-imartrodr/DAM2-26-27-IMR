package adat.ud2.biblioteca;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOtran extends AbstractDAOtran<UsuarioDTO> {

    UsuarioDAOtran(Connection con){
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

    @Override 
    public boolean insertar(UsuarioDTO us) {
        String sql = """
                INSERT INTO usuarios
                (nombre, correo)
                VALUES
                (?, ?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            //sentenciaSQL.setInt(++parIndx, us.getId());
            sentenciaSQL.setString(++parIndx, us.getNombre());
            sentenciaSQL.setString(++parIndx, us.getCorreo());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean borrar(UsuarioDTO us) {
        if (us == null) {
            return false;
        }
        return borrarById(us.getId());
    }

    public boolean borrarById(int id) {
        UsuarioDTO us = findById(id);
        if (us == null || new LibroDAOtran(con).findByPoseedor(us) != null) {
            return false;
        }
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

    @Override 
    public UsuarioDTO[] findAll() {
        List<UsuarioDTO> usuarios = null;

        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM usuarios");
            usuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios.add(new UsuarioDTO(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo")
                    ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios == null ? null : usuarios.toArray(new UsuarioDTO[0]);
    }

    @Override
    public UsuarioDTO find(UsuarioDTO us) {
        if (us == null) {
            return null;
        }
        return findById(us.getId());
    }

    public UsuarioDTO findById(int id) {
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
                        rs.getString("nombre"),
                        rs.getString("correo")
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usRes;
    }

    @Override
    public boolean actualizar(UsuarioDTO usuario) {
        String sql = """
                UPDATE usuarios
                SET nombre = ?,
                    correo = ?
                WHERE ID = ?
                """;
        try {
            int parIndex = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setString(++parIndex, usuario.getNombre());
            sentenciaSQL.setString(++parIndex, usuario.getCorreo());
            sentenciaSQL.setInt(++parIndex, usuario.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
