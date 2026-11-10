package adat.ud2.examen.corregido.dao.impl;

import adat.ud2.examen.corregido.dao.JugadorDAO;
import adat.ud2.examen.corregido.dto.JugadorDTO;
import adat.ud2.examen.corregido.excepciones.AccesoDatosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAOImpl implements JugadorDAO {

    // ============================================================
    // EJERCICIO 4 (0,5 ptos)
    // ============================================================
    @Override
    public List<JugadorDTO> listarJugadoresDeEquipo(int idEquipo, Connection conn) throws SQLException {
        // altura_cm puede ser NULL
        String sql = "SELECT id_jugador, id_equipo, dorsal, nombre, altura_cm FROM jugadores WHERE id_equipo = ? ORDER BY dorsal";
        List<JugadorDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error accediendo a la BD", e);
        }
        return lista;
    }

    // ============================================================
    // EJERCICIO 5 (1 pto) — devuelve null si no existe
    // ============================================================
    @Override
    public JugadorDTO buscarJugador(int id, Connection conn) throws SQLException {
        // si hay fila → devolver el DTO
        // si no hay → devolver null
        String sql = "SELECT id_jugador, id_equipo, dorsal, nombre, altura_cm FROM jugadores WHERE id_jugador = ?";
        JugadorDTO jugador = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    jugador = mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error accediendo a la BD", e);
        }
        return jugador;
    }

    // ============================================================
    // EJERCICIO 6 (1 pto)
    // ============================================================
    @Override
    public int actualizarAlturaJugador(int id, int altura, Connection conn) throws SQLException {
        if (altura < 150 || altura > 230) {
            throw new IllegalArgumentException("Altura fuera de rango [150,230]: " + altura);
        }

        String sql = "UPDATE jugadores SET altura_cm = ? WHERE id_jugador = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, altura);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error accediendo a la BD", e);
        }
    }

    private JugadorDTO mapear(ResultSet rs) throws SQLException {

        Integer alturaCm = rs.getInt("altura_cm");
        if (rs.wasNull()) {
            alturaCm = null;
        }
        return new JugadorDTO(
                rs.getInt("id_jugador"),
                rs.getInt("id_equipo"),
                rs.getInt("dorsal"),
                rs.getString("nombre"),
                alturaCm);
    }
}