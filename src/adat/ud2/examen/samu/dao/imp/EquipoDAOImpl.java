package adat.ud2.examen.samu.dao.imp;

import adat.ud2.examen.samu.dao.EquipoDAO;
import adat.ud2.examen.samu.dto.EquipoDTO;
import adat.ud2.examen.samu.excepciones.AccesoDatosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAOImpl implements EquipoDAO {

    // ============================================================
    // EJERCICIO 1 (0,5 ptos)
    // ============================================================
    @Override
    public List<EquipoDTO> listarEquipos(Connection conn) throws SQLException {
        List<EquipoDTO> lista = null;

        String sql = """
                select * from equipos order by nombre asc
                """;

        try (Statement sentencia = conn.createStatement()) {
            try (ResultSet rs = sentencia.executeQuery(sql);) {
                lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(new EquipoDTO(rs.getInt("id_equipo"), rs.getString("nombre")));
                }
            }

        }

        return lista;
    }

    // ============================================================
    // EJERCICIO 2 (1,5 ptos)
    // ============================================================
    @Override
    public int insertarEquipo(EquipoDTO equipo, Connection conn) throws SQLException {
        int claveGenerada = -1;
        int id = equipo.idEquipo();
        String nombre = equipo.nombre();

        String sql = """
                insert into equipos
                (id_equipo,nombre)
                values(?,?)
                """;
        try (PreparedStatement sentenciaParametrizada = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            sentenciaParametrizada.setInt(1, id);
            sentenciaParametrizada.setString(2, nombre);

            sentenciaParametrizada.executeUpdate();

            try (ResultSet rs = sentenciaParametrizada.getGeneratedKeys()) {
                if (rs.next()) {
                    claveGenerada = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            if (e.getLocalizedMessage().equals(
                    "[SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: equipos.id_equipo)")) {
                throw new AccesoDatosException("Equipo duplicado: " + nombre);
            } else {
                throw new SQLException(e);
            }

        }

        return claveGenerada;
    }

    // ============================================================
    // COMPLETO (referencia)
    // ============================================================
    @Override
    public boolean eliminarEquipo(int id, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM jugadores WHERE id_equipo = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0)
                    return false;
            }
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM partidos WHERE id_local = ? OR id_visitante = ?")) {
            ps.setInt(1, id);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0)
                    return false;
            }
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM equipos WHERE id_equipo = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }
}