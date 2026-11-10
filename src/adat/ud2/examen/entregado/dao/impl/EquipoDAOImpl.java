package adat.ud2.examen.entregado.dao.impl;

import adat.ud2.examen.entregado.dao.EquipoDAO;
import adat.ud2.examen.entregado.dto.EquipoDTO;
import adat.ud2.examen.entregado.excepciones.AccesoDatosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAOImpl implements EquipoDAO {

    // ============================================================
    // EJERCICIO 1 (0,5 ptos)
    // ============================================================
    @Override
    public List<EquipoDTO> listarEquipos(Connection conn) throws SQLException {
        String sql = "SELECT id_equipo, nombre FROM equipos ORDER BY nombre";
        List<EquipoDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error accediendo a la BD", e);
        }
        return lista;
    }

    // ============================================================
    // EJERCICIO 2 (1,5 ptos)
    // ============================================================
    @Override
    public int insertarEquipo(EquipoDTO equipo, Connection conn) throws SQLException {
        String sql = "INSERT INTO equipo (nombre) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, equipo.nombre());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            } catch (SQLException e) {
                throw new AccesoDatosException("Error accediendo a la BD", e);
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Equipo duplicado: " + equipo.nombre(), e);
        }
        return -1;
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
    private EquipoDTO mapear(ResultSet rs) throws SQLException {
        return new EquipoDTO(
            rs.getInt("id_equipo"),
            rs.getString("nombre")
        );
    }
}