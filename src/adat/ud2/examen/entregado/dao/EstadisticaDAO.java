package adat.ud2.examen.entregado.dao;

import adat.ud2.examen.entregado.dto.EstadisticaDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface EstadisticaDAO {

    void insertar(EstadisticaDTO e, Connection conn) throws SQLException;

    /** @return filas borradas */
    int borrarPorPartido(int idPartido, Connection conn) throws SQLException;
}