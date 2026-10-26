package adat.ud2.biblioteca.daoYdto;

import java.sql.Connection;
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
    public abstract T findById(int id);
    public abstract T[] findAll();
}
