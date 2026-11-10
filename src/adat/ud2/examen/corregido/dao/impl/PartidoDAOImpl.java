package adat.ud2.examen.corregido.dao.impl;

import adat.ud2.examen.corregido.dao.PartidoDAO;
import adat.ud2.examen.corregido.dto.PartidoDTO;
import adat.ud2.examen.corregido.dto.RankingDTO;
import adat.ud2.examen.corregido.excepciones.AccesoDatosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        //       si hay fila → mapear y devolver
        //       si no       → devolver null
        //       puntos_local / puntos_visitante pueden ser NULL

        // Comentario de Ignacio MR: el mapeado se debe encargar de manejar que sean NULL, no yo aquí
        String sql = SELECT_BASE + " WHERE p.id_partido = ?";
        PartidoDTO partido = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    partido = mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error accediendo a la BD", e);
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
                while (rs.next()) out.add(mapear(rs));
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
        // TODO: JOIN estadisticas+juegos+equipos+partidos WHERE estado='JUGADO'
        //       GROUP BY jugador, equipo · ORDER BY total DESC
        String sql = """
        SELECT  e.id_estadistica, e.id_jugador, e.id_partido, e.puntos, j.nombre AS nom_jugador, eq.id_equipo, 
                eq.nombre AS nom_equipo
        FROM estadisticas e 
        JOIN jugadores j ON e.id_jugador = j.id_jugador 
        JOIN equipos eq ON j.id_equipo = eq.id_equipo
        JOIN partidos p ON e.id_partido = p.id_partido AND p.estado='JUGADO'
        """;
        //"JOIN estadisticas+juegos+equipos+partidos WHERE estado='JUGADO' GROUP BY jugador, equipo · ORDER BY total DESC";
        Map<Integer, String[]> ranking = new HashMap<>();
        List<RankingDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            String[] valores;
            Integer key;
            while (rs.next()) {
                //Obtengo el jugador unico de cada equipo
                key = rs.getInt("id_jugador");

                //Obtengo sus puntos totales
                valores = ranking.getOrDefault(key, new String[]{rs.getString("nom_jugador"),rs.getString("nom_equipo"), "0"});
                valores[2] = String.valueOf(Integer.valueOf(valores[2]) + rs.getInt("puntos"));

                //Los guardo todos individualmente
                ranking.put(key, valores);
            }
            List<Entry<Integer, String[]>> entradas = new ArrayList<>(ranking.entrySet());
            Collections.sort(entradas, new Comparator<Map.Entry<Integer,String[]>>() {
                @Override
                public int compare(Entry<Integer, String[]> o1, Entry<Integer, String[]> o2) {
                    return Integer.compare(Integer.valueOf(o2.getValue()[2]), Integer.valueOf(o1.getValue()[2]));
                }
            });

            for (Entry<Integer, String[]> entrada : entradas) {
                lista.add(new RankingDTO(entrada.getValue()[0], entrada.getValue()[1], Integer.valueOf(entrada.getValue()[2])));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error accediendo a la BD", e);
        } catch (NumberFormatException n) {
            throw new AccesoDatosException("Error calculando los puntos", n);
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