package adat.ud2.h2bd.biblioteca.daoYdto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public abstract class AbstractDAOtran<T extends AbstractDTO>{
    protected Connection con;

    protected AbstractDAOtran(Connection con){
        if (con == null) {
            throw new IllegalArgumentException("La conexion no debe ser null");
        }
        this.con = con;
    }

    public boolean borrar(T dto) {
        if (dto == null || !exists(dto) || !isBorradoPosible(dto)) {
            return false;
        }
        return delete(dto);
    }

    public boolean borrarById(int id){
        T dto = findById(id);
        if (dto == null || !isBorradoPosible(dto)) {
           return false; 
        }
        return delete(dto);
    }
    
    protected boolean delete(T dto){
        String sql = "DELETE FROM " + getTablaName().strip() + " WHERE ID = ?";
        try {
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(1, dto.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public abstract boolean isBorradoPosible(T dto);

    public abstract int insertar(T dto);

    public abstract boolean actualizar(T dto);

    public T findById(int id){
        if (id < 0) {
            return null;
        }
        T dtoRes = null;
        String sql = "SELECT * FROM " + getTablaName().strip() + " WHERE id = ?";
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            T[] dtos = parseRs(rs);
            if (dtos != null && dtos.length > 0) {
                dtoRes = dtos[0];
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtoRes;
    }

    public T[] findAll() {
        T[] dtos = null;
        String sql = "SELECT * FROM " + getTablaName().strip();
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            ResultSet rs = sentenciaSQL.executeQuery();
            dtos = parseRs(rs);
            Arrays.sort(dtos);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (dtos == null || dtos.length == 0) ? null : dtos;
    }
/* 
    public boolean exists(int id){
        if (id < 0) {
            return false;
        }
        boolean exists = false;
        String sql = "SELECT id FROM " + getTablaName().strip() + " WHERE id = ?";
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                exists = true;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    } */

    public boolean exists(T dto){
        if (dto == null || dto.getId() < 0) {
            return false;
        }
        boolean valid = false;
        String sql = "SELECT * FROM " + getTablaName().strip() + " WHERE id = ?";
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, dto.getId());
            ResultSet rs = sentenciaSQL.executeQuery();
            T[] dtos = parseRs(rs);
            if (dtos != null) {
                valid = dto.equals(dtos[0]);//TODO comprobar si funciona
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valid;
    }
    
    public abstract T[] parseRs(ResultSet rs) throws SQLException;

    public abstract String getTablaName();
}
