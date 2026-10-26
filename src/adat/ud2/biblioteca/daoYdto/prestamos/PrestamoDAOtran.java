package adat.ud2.biblioteca.daoYdto.prestamos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDTO;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDTO;

public class PrestamoDAOtran extends AbstractDAOtran<PrestamoDTO> {

    public PrestamoDAOtran(Connection con){
        super(con);
    }

    @Override
    public boolean borrarById(int id) {
        if (id < 0) {
            return false;
        }
        String sql = """
                DELETE FROM prestamos
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
    public boolean insertar(PrestamoDTO prest) {
        String sql = """
                INSERT INTO prestamos
                (id_usuario, id_libro)
                VALUES
                (?, ?)
                """;
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
        String sql = """
                UPDATE prestamos
                SET id_usuario = ?,
                    id_libro = ?
                WHERE ID = ?
                """;
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

    @Override
    public PrestamoDTO findById(int id) {
        if (id < 0) {
            return null;
        }
        PrestamoDTO prestRes = null;
        String sql = """
                SELECT * FROM prestamos
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                prestRes = new PrestamoDTO(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_libro")
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestRes;
    }

    @Override
    public PrestamoDTO[] findAll() {
        List<PrestamoDTO> prestamos = null;

        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM libros");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos == null ? null : prestamos.toArray(new PrestamoDTO[0]);
    }

    public LibroDTO[] findByUsuario(UsuarioDTO us) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsuario'");
    }

    public UsuarioDTO[] findByLibro(LibroDTO us) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsuario'");
    }
}
