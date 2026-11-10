package adat.ud2.examen.samu.dao.imp;


import adat.ud2.examen.samu.dao.JugadorDAO;
import adat.ud2.examen.samu.dto.JugadorDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAOImpl implements JugadorDAO {

    // ============================================================
    // EJERCICIO 4 (0,5 ptos)
    // ============================================================
    @Override
    public List<JugadorDTO> listarJugadoresDeEquipo(int idEquipo, Connection conn)
            throws SQLException {

        List<JugadorDTO> lista = null;
        String sql = """
                select * from jugadores
                where id_equipo = ?
                """;

        try (PreparedStatement sentenciaParametrizada = conn.prepareStatement(sql)) {

            sentenciaParametrizada.setInt(1, idEquipo);

            try (ResultSet rs = sentenciaParametrizada.executeQuery()) {
                lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(new JugadorDTO(rs.getInt("id_jugador"), rs.getInt("id_equipo"), rs.getInt("dorsal"),
                            rs.getString("nombre"), rs.getInt("altura_cm")));
                }
            }

        }

        return lista;
    }

    // ============================================================
    // EJERCICIO 5 (1 pto) — devuelve null si no existe
    // ============================================================
    @Override
    public JugadorDTO buscarJugador(int id, Connection conn) throws SQLException {
        JugadorDTO jugador = null;

        String sql = """
                select * from jugadores
                where id_jugador = ?
                """;

        try (PreparedStatement senteciaParametrizada = conn.prepareStatement(sql)) {
            senteciaParametrizada.setInt(1, id);
            try (ResultSet rs = senteciaParametrizada.executeQuery()) {
                if (rs.next()) {
                    jugador = new JugadorDTO(rs.getInt("id_jugador"), rs.getInt("id_equipo"), rs.getInt("dorsal"),
                            rs.getString("nombre"), rs.getInt("altura_cm"));
                }
            }

        }

        return jugador;
    }

    // ============================================================
    // EJERCICIO 6 (1 pto)
    // ============================================================
    @Override
    public int actualizarAlturaJugador(int id, int altura, Connection conn)
            throws SQLException {
        int filasAlteradas = -1;

        if (altura < 150 || altura > 230) {
            throw new IllegalArgumentException(
                    "Altura fuera de rango [150,230]: " + altura);
        }

        String sql = """
                UPDATE jugadores SET altura_cm = ? WHERE id_jugador = ?
                """;

        try (PreparedStatement sentenciaParametrizada = conn.prepareStatement(sql)) {
            sentenciaParametrizada.setInt(1, altura);
            sentenciaParametrizada.setInt(2, id);

            filasAlteradas = sentenciaParametrizada.executeUpdate();
        }

        return filasAlteradas;
    }
}