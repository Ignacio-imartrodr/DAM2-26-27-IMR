package adat.ud2.biblioteca;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAOtran<T extends AbstractDTO> {
    protected Connection con;

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
    public abstract boolean borrar(T dto);

    public abstract boolean insertar(T dto);

    public abstract boolean actualizar(T dto);

    public abstract T find(T dto);

    public abstract T[] findAll();
}
