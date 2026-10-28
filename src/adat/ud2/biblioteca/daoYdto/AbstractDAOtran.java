package adat.ud2.biblioteca.daoYdto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAOtran<T extends AbstractDTO>{
    protected Connection con;

    protected AbstractDAOtran(Connection con){
        if (con == null) {
            throw new IllegalArgumentException("La conexion no debe ser null");
        }
        this.con = con;
    }

    public boolean beginTransaction(){
        boolean isSuccess = false;
        try {
            if (!con.getAutoCommit()) {
                System.err.println("Transacción ya en curso");
            } else {
                con.setAutoCommit(false);
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean endTransaction(boolean hasSucceded){
        boolean isSuccess = false;
        
        try {
            if (con.getAutoCommit()) {
                System.err.println("No hay ninguna transacción ya en curso");
                return isSuccess;
            }
            if (hasSucceded) {
                con.commit();
            } else {
                con.rollback();
            }
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public boolean borrar(T dto) {
        if (dto == null) {
            return false;
        }
        return borrarById(dto.getId());
    }
    public abstract boolean borrarById(int id);

    public abstract boolean insertar(T dto);

    public abstract boolean actualizar(T dto);

    public T find(T dto) {
        if (dto == null) {
            return null;
        }
        return findById(dto.getId());
    }

    public T findById(int id){
        if (id < 0) {
            return null;
        }
        T libroRes = null;
        String sql = "SELECT * FROM " + getTablaName().strip() + " WHERE id = ?";
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, id);
            ResultSet rs = sentenciaSQL.executeQuery();
            T[] libros = parseRs(rs);
            if (libros.length > 0) {
                libroRes = libros[0];
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libroRes;
    }

    public T[] findAll() {
        T[] prestamos = null;
        String sql = "SELECT * FROM prestamos";
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            ResultSet rs = sentenciaSQL.executeQuery();
            prestamos = parseRs(rs);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (prestamos == null || prestamos.length == 0) ? null : prestamos;
    }

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
    }

    public boolean validate(T dto){
        if (dto.getId() < 0) {
            return false;
        }
        boolean exists = false;
        String sql = "SELECT * FROM " + getTablaName().strip() + " WHERE id = ?";
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql)){
            sentenciaSQL.setInt(1, dto.getId());
            ResultSet rs = sentenciaSQL.executeQuery();
            parseRs(rs);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    
    public abstract T[] parseRs(ResultSet rs);

    public abstract String getTablaName();
}
