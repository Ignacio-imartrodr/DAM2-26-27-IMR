package adat.ud2.examen.samu.dao.imp;


import adat.ud2.examen.samu.dao.PartidoDAO;
import adat.ud2.examen.samu.dto.PartidoDTO;
import adat.ud2.examen.samu.dto.RankingDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAOImpl implements PartidoDAO {

    private static final String SELECT_BASE = """
            SELECT p.id_partido, p.id_local, p.id_visitante, p.fecha,
                   p.puntos_local, p.puntos_visitante, p.estado,
                   el.nombre AS nom_local, ev.nombre AS nom_visitante
            FROM partidos p
            JOIN equipos el ON el.id_equipo = p.id_local
            JOIN equipos ev ON ev.id_equipo = p.id_visitante
            """;

    // ============================================================
    // EJERCICIO 3 (1,5 ptos) — devuelve null si no existe
    // ============================================================
    @Override
    public PartidoDTO buscarPartido(int id, Connection conn) throws SQLException {
        PartidoDTO partido = null;

        String sql = """
                select p.id_partido,p.id_local,p.id_visitante,p.fecha,p.puntos_local,p.puntos_visitante,p.estado,l.nombre as nom_local,v.nombre as nom_visitante from partidos p
                join equipos l on l.id_equipo = p.id_local
                join equipos v on v.id_equipo = p.id_local
                where p.id_partido = ?
                """;

        try (PreparedStatement sentenciaParametrizada = conn.prepareStatement(sql)) {

            sentenciaParametrizada.setInt(1, id);

            try (ResultSet rs = sentenciaParametrizada.executeQuery();) {
                partido = mapear(rs);
            }

        }
        return partido;

    }

    // ============================================================
    // COMPLETO (referencia) — usado por el Main
    // ============================================================
    @Override
    public List<PartidoDTO> listarPartidosPorEstado(String estado, Connection conn)
            throws SQLException {
        String sql = SELECT_BASE + " WHERE p.estado = ? ORDER BY p.fecha";
        List<PartidoDTO> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    out.add(mapear(rs));
            }
        }
        return out;
    }

    @Override
    public int actualizarResultado(int id, int pl, int pv, Connection conn)
            throws SQLException {
        String sql = """
                UPDATE partidos
                   SET puntos_local = ?, puntos_visitante = ?, estado = 'JUGADO'
                 WHERE id_partido = ? AND estado = 'PENDIENTE'
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pl);
            ps.setInt(2, pv);
            ps.setInt(3, id);
            return ps.executeUpdate();
        }
    }

    @Override
    public int actualizarEstado(int id, String estado, Connection conn)
            throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE partidos SET estado = ? WHERE id_partido = ?")) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    // ============================================================
    // EJERCICIO 7 (2 ptos)
    // ============================================================
    @Override
    public List<RankingDTO> rankingAnotadores(Connection conn) throws SQLException {
        List<RankingDTO> lista = null;

        String sql = """
                select j.nombre as jugador, e.nombre as equipo ,sum(es.puntos) as total from partidos p
                join estadisticas es on es.id_partido = p.id_partido
                join jugadores j on j.id_jugador = es.id_jugador
                join equipos e on e.id_equipo = j.id_equipo
                where p.estado='JUGADO'
                group by jugador, equipo
                order by total DESC
                """;

        try (PreparedStatement sentenciaParametrizada = conn.prepareStatement(sql)) {
            try (ResultSet rs = sentenciaParametrizada.executeQuery()) {
                lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(new RankingDTO(rs.getString("jugador"), rs.getString("equipo"), rs.getInt("total")));
                }
            }
        }

        return lista;
    }

    // ============================================================
    // Utilidad de mapeo (NO modificar)
    // ============================================================
    private PartidoDTO mapear(ResultSet rs) throws SQLException {
        return new PartidoDTO(
                rs.getInt("id_partido"),
                rs.getInt("id_local"),
                rs.getInt("id_visitante"),
                rs.getString("fecha"),
                (Integer) rs.getObject("puntos_local"),
                (Integer) rs.getObject("puntos_visitante"),
                rs.getString("estado"),
                rs.getString("nom_local"),
                rs.getString("nom_visitante"));
    }
}