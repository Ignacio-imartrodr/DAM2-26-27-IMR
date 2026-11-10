package adat.ud2.examen.samu.dao.imp;


import adat.ud2.examen.samu.dao.EstadisticaDAO;
import adat.ud2.examen.samu.dto.EstadisticaDTO;

import java.sql.*;

public class EstadisticaDAOImpl implements EstadisticaDAO {

    // ============================================================
    // EJERCICIO 8 (0,5 ptos)
    // ============================================================
    @Override
    public void insertar(EstadisticaDTO e, Connection conn) throws SQLException {

        String sql = """
                INSERT INTO estadisticas(id_partido, id_jugador, puntos) VALUES (?,?,?)
                """;

        try (PreparedStatement sentenciaParametrizada = conn.prepareStatement(sql)) {
            sentenciaParametrizada.setInt(1, e.idPartido());
            sentenciaParametrizada.setInt(2, e.idJugador());
            sentenciaParametrizada.setInt(3, e.puntos());

            sentenciaParametrizada.executeUpdate();
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