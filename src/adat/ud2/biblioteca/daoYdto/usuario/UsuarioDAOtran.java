package adat.ud2.biblioteca.daoYdto.usuario;

import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.biblioteca.daoYdto.prestamos.PrestamoDAOtran;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOtran extends AbstractDAOtran<UsuarioDTO> {

    public UsuarioDAOtran(Connection con){
        super(con);
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
            sentenciaSQL.setString(++parIndx, us.getNombre());
            sentenciaSQL.setString(++parIndx, us.getCorreo());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override 
    public boolean borrarById(int id) {
        UsuarioDTO us = findById(id);
        if (us == null || new PrestamoDAOtran(con).findByUsuario(us) != null) {
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
            if (rs.next()) {
                usuarios = new ArrayList<>();
                do {
                    usuarios.add(new UsuarioDTO(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo")
                        ));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios == null ? null : usuarios.toArray(new UsuarioDTO[0]);
    }

    @Override 
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
    
    @Override
    public String getTablaName() {
        return "usuarios";
    }
}
