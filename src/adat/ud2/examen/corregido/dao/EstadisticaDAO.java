package adat.ud2.examen.corregido.dao;

import adat.ud2.examen.corregido.dto.EstadisticaDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface EstadisticaDAO {

    void insertar(EstadisticaDTO e, Connection conn) throws SQLException;

    /** @return filas borradas */
    int borrarPorPartido(int idPartido, Connection conn) throws SQLException;
}