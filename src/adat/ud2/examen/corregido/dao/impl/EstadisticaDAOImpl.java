package adat.ud2.examen.corregido.dao.impl;

import adat.ud2.examen.corregido.dao.EstadisticaDAO;
import adat.ud2.examen.corregido.dto.EstadisticaDTO;
import adat.ud2.examen.corregido.excepciones.AccesoDatosException;

import java.sql.*;

public class EstadisticaDAOImpl implements EstadisticaDAO {

    // ============================================================
    // EJERCICIO 8 (0,5 ptos)
    // ============================================================
    @Override
    public void insertar(EstadisticaDTO e, Connection conn) throws SQLException {
        String sql = "INSERT INTO estadisticas(id_partido, id_jugador, puntos) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.idPartido());
            ps.setInt(2, e.idJugador());
            ps.setInt(3, e.puntos());
            ps.executeUpdate();
        } catch (SQLException e1) {
                throw new AccesoDatosException("Error accediendo a la BD", e1);
        }
    }

    // ============================================================
    // COMPLETO — lo necesita cancelarPartido (ejercicio 9)
    // ============================================================
    @Override
    public int borrarPorPartido(int idPartido, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM estadisticas WHERE id_partido = ?")) {
            ps.setInt(1, idPartido);
            return ps.executeUpdate();
        }
    }
}