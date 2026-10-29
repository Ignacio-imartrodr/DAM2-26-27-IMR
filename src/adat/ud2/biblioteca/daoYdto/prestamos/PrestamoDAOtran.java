package adat.ud2.biblioteca.daoYdto.prestamos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDTO;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDAOtran;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDTO;

public class PrestamoDAOtran extends AbstractDAOtran<PrestamoDTO> {

    public PrestamoDAOtran(Connection con){
        super(con);
    }

    @Override
    public boolean insertar(PrestamoDTO prest) {
        String sql = "INSERT INTO " + getTablaName();
        sql.concat("""
                 (id_usuario, id_libro)
                VALUES
                (?, ?)""");
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(++parIndx, prest.getIdUsuario());
            sentenciaSQL.setInt(++parIndx, prest.getIdLibro());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(PrestamoDTO prest) {
        String sql = "UPDATE " + getTablaName();
        sql.concat("""
                 SET id_usuario = ?,
                    id_libro = ?
                WHERE ID = ?
                """);
        try {
            int parIndex = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(++parIndex, prest.getIdUsuario());
            sentenciaSQL.setInt(++parIndex, prest.getIdLibro());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PrestamoDTO[] findByUsuario(UsuarioDTO us) {
        if (us.getId() < 0) {
            return null;
        }
        PrestamoDTO[] prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM " + getTablaName() + " WHERE id_usuario = " + us.getId());
            prestamos = parseRs(rs);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public Integer[] getIdsByUsuario(UsuarioDTO us) {
        if (!(new UsuarioDAOtran(con).exists(us))) {
            return null;
        }
        List<Integer> prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT id FROM " + getTablaName() + " WHERE id_usuario = " + us.getId());
            if (rs.next()) {
                prestamos = new ArrayList<>();
                do {
                    prestamos.add(rs.getInt("id"));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos == null || prestamos.size() == 0 ? null : prestamos.toArray(new Integer[0]);
    }

    public PrestamoDTO[] findByLibro(LibroDTO libro) {
        if (libro.getId() < 0) {
            return null;
        }
        PrestamoDTO[] prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM " + getTablaName() + " WHERE id_libro = " + libro.getId());
            prestamos = parseRs(rs);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public Integer[] getIdsByLibro(LibroDTO libro) {
        if (!(new LibroDAOtran(con).exists(libro))) {
            return null;
        }
        List<Integer> prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()) {
            ResultSet rs = sentenciaSQL.executeQuery("SELECT id FROM " + getTablaName() + " WHERE id_libro = " + libro.getId());
            if (rs.next()) {
                prestamos = new ArrayList<>();
                do {
                    prestamos.add(rs.getInt("id"));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos == null || prestamos.size() == 0 ? null : prestamos.toArray(new Integer[0]);
    }

    @Override
    public String getTablaName() {
        return "prestamos";
    }

    @Override
    public PrestamoDTO[] parseRs(ResultSet rs) throws SQLException {
        List<PrestamoDTO> prestamos = null;
        if (rs.next()) {
            prestamos = new ArrayList<>();
            do {
                prestamos.add(new PrestamoDTO(
                        rs.getInt("id"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_usuario")
                    ));
            } while (rs.next());
        }
        rs.close();
        return prestamos == null ? null : prestamos.toArray(new PrestamoDTO[0]);
    }

    @Override
    public boolean isBorradoPosible(PrestamoDTO presamo) {
        return presamo != null;
    }
}