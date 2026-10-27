package adat.ud2.biblioteca.daoYdto.genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;

public class GeneroDAOtran extends AbstractDAOtran<GeneroDTO> {

    public GeneroDAOtran(Connection con) {
        super(con);
    }

    @Override
    public GeneroDTO findById(int id) {
        if (id < 0) {
            return null;
        }
        GeneroDTO generoRes = null;
        String sql = """
                SELECT * FROM generos
                WHERE id = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                generoRes = new GeneroDTO(
                        rs.getInt("id"),
                        rs.getString("nombre")
                    );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generoRes;
    }

    @Override
    public boolean borrarById(int id) {
        if (id < 0) {
            return false;
        }
        String sql = """
                DELETE FROM generos
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
    public boolean insertar(GeneroDTO genero) {
         String sql = """
                INSERT INTO generos
                (nombre)
                VALUES
                (?)
                """;
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setString(++parIndx, genero.getNombre());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println("Ya existe un genero con el nombre \"" + genero.getNombre() + "\"");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean actualizar(GeneroDTO genero) {
        String sql = """
                UPDATE generos
                SET nombre = ?
                WHERE ID = ?
                """;
        try {
            int parIndex = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setString(++parIndex, genero.getNombre());
        
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println("Ya existe un genero con el nombre \"" + genero.getNombre() + "\"");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public GeneroDTO[] findAll() {
        List<GeneroDTO> generos = null;

        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM usuarios");
            if (rs.next()) {
                generos = new ArrayList<>();
                do {
                    generos.add(new GeneroDTO(
                            rs.getInt("id"),
                            rs.getString("nombre")
                        ));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generos == null ? null : generos.toArray(new GeneroDTO[0]);
    }

    @Override
    public String getTablaName() {
        return "generos";
    }
}
