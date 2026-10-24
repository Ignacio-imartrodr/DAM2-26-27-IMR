package adat.ud2.CRUD_DAO_DTO_Profesor_Scarbmora.CRUD_DAO_DTO_Profesor_Scarbmora;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

public class ProfesorDAO {
    private static String url = "jdbc:h2:file:./BDs/Instituto/instituto";
    private static String user = "sa";
    private static String password = "";

    public List<ProfesorDTO> listarProfesores() {
        List<ProfesorDTO> profesores = null;

        try (Connection conexion = DriverManager.getConnection(url, user, password);
                Statement ejecutor = conexion.createStatement();) {

            try (ResultSet rs = ejecutor.executeQuery("select * from profesores")) {
                profesores = new ArrayList<>();
                while (rs.next()) {
                    profesores.add(new ProfesorDTO(rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("direccion"),
                            rs.getString("telefono")));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return profesores;

    }

    public boolean insertarProfesor(ProfesorDTO profesor) {
        try (Connection conexion = DriverManager.getConnection(url, user, password)) {

            String sql = """
                    INSERT INTO profesores
                    VALUES
                    (DEFAULT, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement sentencia = conexion.prepareStatement(sql);) {

                sentencia.setString(1, profesor.getNombre());
                sentencia.setString(2, profesor.getApellidos());
                sentencia.setDate(3, Date.valueOf(profesor.getFechaNacimiento()));
                sentencia.setString(4, profesor.getDireccion());
                sentencia.setString(5, profesor.getTelefono());

                return sentencia.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public ProfesorDTO buscarProfesor(ProfesorDTO profesor) {
        ProfesorDTO profesorResultante = null;

        try (Connection conexion = DriverManager.getConnection(url, user, password)) {

            String buscarProfesor = """
                    select * from profesores where id=?
                    """;

            try (PreparedStatement sentencia = conexion.prepareStatement(buscarProfesor);) {

                sentencia.setInt(1, profesor.getId());

                try (ResultSet rs = sentencia.executeQuery();) {
                    while (rs.next()) {
                        profesorResultante = new ProfesorDTO(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellidos"),
                                rs.getDate("fecha_nacimiento").toLocalDate(),
                                rs.getString("direccion"),
                                rs.getString("telefono"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return profesorResultante;

    }

    public ProfesorDTO buscarProfesor(int id) {
        ProfesorDTO profesorResultante = null;
        try (Connection conexion = DriverManager.getConnection(url, user, password)) {

            String buscarProfesor = """
                    select * from profesores where id = ?
                    """;

            try (PreparedStatement sentencia = conexion.prepareStatement(buscarProfesor)) {

                sentencia.setInt(1, id);

                try (ResultSet rs = sentencia.executeQuery();) {
                    while (rs.next()) {
                        profesorResultante = new ProfesorDTO(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellidos"),
                                rs.getDate("fecha_nacimiento").toLocalDate(),
                                rs.getString("direccion"),
                                rs.getString("telefono"));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return profesorResultante;

    }

    public boolean actualizarProfesor(ProfesorDTO profesor) {

        Boolean resultado = false;
        try (Connection conextion = DriverManager.getConnection(url, user, password)) {

            String actualizarProfesor = """
                    update profesores
                    SET nombre = ?,
                    apellidos = ?,
                    fecha_nacimiento = ?,
                    direccion = ?,
                    telefono = ?
                    where id = ?
                    """;

            try (PreparedStatement sentencia = conextion.prepareStatement(actualizarProfesor)) {

                sentencia.setString(1, profesor.getNombre());
                sentencia.setString(2, profesor.getApellidos());
                sentencia.setDate(3, Date.valueOf(profesor.getFechaNacimiento()));
                sentencia.setInt(6, profesor.getId());
                sentencia.setString(4, profesor.getDireccion());
                sentencia.setString(5, profesor.getTelefono());

                resultado = sentencia.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return resultado;
    }

    public boolean borrarProfesor(ProfesorDTO profesor) {
        Boolean isDeleted = false;
        try (Connection conexion = DriverManager.getConnection(url, user, password)) {

            String borrarProfesor = """
                    delete from profesores
                    where id = ?
                    """;

            try (PreparedStatement sentencia = conexion.prepareStatement(borrarProfesor)) {

                sentencia.setInt(1, profesor.getId());

                isDeleted = sentencia.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDeleted;

    }

    public boolean borrarProfesor(int id) {
        Boolean isDeleted = false;
        try (Connection conexion = DriverManager.getConnection(url, user, password)) {

            String borrarProfesor = """
                    delete from profesores
                    where id = ?
                    """;

            try (PreparedStatement sentencia = conexion.prepareStatement(borrarProfesor)) {

                sentencia.setInt(1, id);

                isDeleted = sentencia.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;

    }
}
