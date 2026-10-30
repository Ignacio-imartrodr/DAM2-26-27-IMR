package adat.ud2.h2bd.biblioteca.daoYdto.usuario;

import java.util.ArrayList;
import java.util.List;

import adat.ud2.h2bd.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.h2bd.biblioteca.daoYdto.prestamos.PrestamoDAOtran;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAOtran extends AbstractDAOtran<UsuarioDTO> {

    public UsuarioDAOtran(Connection con){
        super(con);
    }

    @Override 
    public int insertar(UsuarioDTO us) {
        String sql = "INSERT INTO " + getTablaName() + " (nombre, correo) VALUES (?, ?)";
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentenciaSQL.setString(++parIndx, us.getNombre());
            sentenciaSQL.setString(++parIndx, us.getCorreo());
            if (sentenciaSQL.executeUpdate() <= 0) {
                return -1;
            }
            try (ResultSet claves = sentenciaSQL.getGeneratedKeys()) {
                return claves.next() ? claves.getInt(1) : -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean actualizar(UsuarioDTO usuario) {
        String sql = "UPDATE " + getTablaName() + " SET nombre = ?, correo = ? WHERE ID = ?";
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

    @Override
    public UsuarioDTO[] parseRs(ResultSet rs) throws SQLException {
        List<UsuarioDTO> usuarios = null;
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
        return usuarios == null ? null : usuarios.toArray(new UsuarioDTO[0]);
    }

    @Override
    public boolean isBorradoPosible(UsuarioDTO us) {
        if (us == null || new PrestamoDAOtran(con).findByUsuario(us) != null) {
            return false;
        }
        return true;
    }
}