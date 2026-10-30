package adat.ud2.sqlLite.bdColaborativa.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.sqlLite.bdColaborativa.dto.AlumnoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ignacio MR
 * AlumnoDAOImplTran
 */
public class AlumnoDAOImplTran implements AlumnoDAO {
    private Connection con;

    public AlumnoDAOImplTran(Connection con) {
         this.con = con; 
    }

    /**    (non-Javadoc)
     * Returns
     * @see adat.ud2.sqlLite.bdColaborativa.dao.AlumnoDAO#listarTodos()
     */
    @Override 
    public List<AlumnoDTO> listarTodos() {
        List<AlumnoDTO> alumnos = null;

        String sql = "SELECT * FROM alumnos";
        try (   Statement sentenciaSQL = con.createStatement();
                ResultSet rs = sentenciaSQL.executeQuery(sql);) {
            if (rs.next()) {
                alumnos = new ArrayList<>();
                do {
                    alumnos.add(new AlumnoDTO(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("curso")));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override 
    public int insertar(AlumnoDTO alumno) {
        String sql = """
                INSERT INTO alumnos
                (nombre, apellidos, curso)                
                VALUES
                (?, ?, ?)
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
            sentenciaSQL.setString(1, alumno.getNombre());
            sentenciaSQL.setString(2, alumno.getApellidos());
            sentenciaSQL.setString(3, alumno.getCurso());
            if (sentenciaSQL.executeUpdate() > 0) {
                try (ResultSet claves = sentenciaSQL.getGeneratedKeys()) {
                    if (claves.next()) {
                        return claves.getInt(1);
                    }
                    // Si no generó claves, sale por -1
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return  -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override 
    public boolean eliminar(int id) {
        String sql = """
                DELETE FROM alumnos
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql);) {
            sentenciaSQL.setInt(1, id);
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override 
    public AlumnoDTO buscarPorId(int id) {
        AlumnoDTO alumnoRes = null;

        String sql = """
                SELECT * FROM alumnos
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql);) {

            sentenciaSQL.setInt(1, id);

            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                alumnoRes = new AlumnoDTO(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("curso"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnoRes;
    }

    @Override 
    public boolean actualizar(AlumnoDTO alumno) {
        String sql = """
                UPDATE alumnos
                SET nombre = ?,
                    apellidos = ?,
                    curso = ?
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql);) {
            sentenciaSQL.setString(1, alumno.getNombre());
            sentenciaSQL.setString(2, alumno.getApellidos());
            sentenciaSQL.setString(3, alumno.getCurso());
            
            sentenciaSQL.setInt(4, alumno.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void matricularAlumnos(List<AlumnoDTO> alumnos) {
        try {
            con.setAutoCommit(false);

            // Ejecutar sentencias de la transacción
            for (AlumnoDTO alumnoDTO : alumnos) {
                if (insertar(alumnoDTO) < 0)
                    throw new SQLException();
            }

            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
