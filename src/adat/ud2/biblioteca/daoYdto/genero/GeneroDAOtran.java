package adat.ud2.biblioteca.daoYdto.genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDAOtran;

public class GeneroDAOtran extends AbstractDAOtran<GeneroDTO> {

    public GeneroDAOtran(Connection con) {
        super(con);
    }

    @Override
    public int insertar(GeneroDTO genero) {
        String sql = "INSERT INTO " + getTablaName() + " (nombre) VALUES (?)";
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentenciaSQL.setString(++parIndx, genero.getNombre());
            if (sentenciaSQL.executeUpdate() <= 0) {
                return -1;
            }
            try (ResultSet claves = sentenciaSQL.getGeneratedKeys()) {
                return claves.next() ? claves.getInt(1) : -1;
            }
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println("Ya existe un " + getTablaName().substring(0, getTablaName().length() - 1) + " con el nombre \"" + genero.getNombre() + "\"");
            } else {
                e.printStackTrace();
            }
            return -1;
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
    public String getTablaName() {
        return "generos";
    }

    @Override
    public boolean isBorradoPosible(GeneroDTO dto) {
        if (dto == null || new LibroDAOtran(con).findByGenero(dto) != null) {
            return false;
        }
        return true;
    }

    @Override
    public GeneroDTO[] parseRs(ResultSet rs) throws SQLException {
        List<GeneroDTO> generos = null;
        if (rs.next()) {
            generos = new ArrayList<>();
            do {
                generos.add(new GeneroDTO(
                        rs.getInt("id"),
                        rs.getString("nombre")
                    ));
            } while (rs.next());
        }
        return generos == null || generos.size() == 0 ? null : generos.toArray(new GeneroDTO[0]);
    }
}
