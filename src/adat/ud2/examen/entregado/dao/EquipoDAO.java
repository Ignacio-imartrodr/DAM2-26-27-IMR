package adat.ud2.examen.entregado.dao;

import adat.ud2.examen.entregado.dto.EquipoDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface EquipoDAO {

    /** Todos los equipos ordenados por nombre. */
    List<EquipoDTO> listarEquipos(Connection conn) throws SQLException;

    /**
     * Inserta un equipo.
     * @return id_equipo autogenerado
     * @throws adat.ud2.examen.entregado.excepciones.AccesoDatosException si el nombre ya existe
     */
    int insertarEquipo(EquipoDTO equipo, Connection conn) throws SQLException;

    /**
     * Elimina un equipo solo si no tiene jugadores ni partidos.
     * @return true si se eliminó
     */
    boolean eliminarEquipo(int id, Connection conn) throws SQLException;
}