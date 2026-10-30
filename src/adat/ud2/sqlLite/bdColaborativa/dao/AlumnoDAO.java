package adat.ud2.sqlLite.bdColaborativa.dao;

import java.util.List;

import adat.ud2.sqlLite.bdColaborativa.dto.AlumnoDTO;

/**
 * @author Ignacio MR
 * AlumnoDAO
 */
public interface AlumnoDAO {
    public List<AlumnoDTO> listarTodos();
    public int insertar(AlumnoDTO alumno);
    public boolean eliminar(int id);
    public AlumnoDTO buscarPorId(int id);
    public boolean actualizar(AlumnoDTO alumno);

    // Otros métodos
    public void matricularAlumnos(List<AlumnoDTO> alumnos);
}
