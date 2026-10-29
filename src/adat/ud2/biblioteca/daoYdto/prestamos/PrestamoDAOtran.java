package adat.ud2.biblioteca.daoYdto.prestamos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDTO;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDAOtran;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDTO;

public class PrestamoDAOtran extends AbstractDAOtran<PrestamoDTO> {

    public PrestamoDAOtran(Connection con){
        super(con);
    }

    @Override
    public int insertar(PrestamoDTO prest) {
        boolean notTranPrev = false;
        try {
            notTranPrev = con.getAutoCommit();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        if (notTranPrev) {
            if (!beginTransaction()) {
                return -1;
            }
        }
        LibroDAOtran lDAO = new LibroDAOtran(con);
        LibroDTO l = lDAO.findById(prest.getIdLibro());
        if (l == null) {
            System.out.println("No se encontró el libro");
            if (notTranPrev) {
                endTransaction(false);
            }
            return -1;
        }
        if (!l.prestarCopia()) {
            System.out.println("No quedan copias del libro \"" + l.toString() + "\" para prestar");
            if (notTranPrev) {
                endTransaction(false);
            }
            return -1;
        }
        String sql = "INSERT INTO " + getTablaName() + " (id_usuario, id_libro) VALUES (?, ?)";
        try {
            int parIndx = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentenciaSQL.setInt(++parIndx, prest.getIdUsuario());
            sentenciaSQL.setInt(++parIndx, prest.getIdLibro());
            if (sentenciaSQL.executeUpdate() <= 0 || !lDAO.actualizarCopias(l)) {
                if (notTranPrev) {
                    endTransaction(false);
                }
                return -1;
            }
            try (ResultSet claves = sentenciaSQL.getGeneratedKeys()) {
                int id = claves.next() ? claves.getInt(1) : -1;
                if (notTranPrev) {
                    if (!endTransaction(id > 0)) {
                        return -1;   
                    }
                }
                return id;
            }
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println("Ya existe un " + getTablaName().substring(0, getTablaName().length() - 1) + " con la combinación de usuario y libro de \"" + prest.toString() + "\"");
            } else {
                e.printStackTrace();
            }
            if (notTranPrev) {
                endTransaction(false);
            }
            return -1;
        }
    }

    @Override
    public boolean actualizar(PrestamoDTO prest) {
        PrestamoDTO pPrev = findById(prest.getId());
        if (pPrev == null) {
            System.err.println("No se encontró el id de este prestamo");
            return false;
        }
        boolean notTranPrev = false;
        try {
            notTranPrev = con.getAutoCommit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (notTranPrev) {
            if (!beginTransaction()) {
                return false;
            }
        }
        UsuarioDAOtran uDAO = new UsuarioDAOtran(con);
        if (uDAO.findById(prest.getIdUsuario()) == null) {
            System.err.println("No se encuentra este id de usuario");
            if (notTranPrev) {
                endTransaction(false);
            }
            return false;
        }
        LibroDAOtran lDAO = new LibroDAOtran(con);
        LibroDTO l = lDAO.findById(prest.getIdLibro());
        LibroDTO l2 = lDAO.findById(pPrev.getIdLibro());
        if (l == null || l2 == null) {
            System.err.println("No se encuentra este id de libro");
            if (notTranPrev) {
                endTransaction(false);
            }
            return false;
        }
        boolean sameLibro = l.equals(l2);
        if (!sameLibro) {
            if (!l.prestarCopia()) {
                System.err.println("No quedan copias del libro \"" + l.toString() + "\" para prestar");
                if (notTranPrev) {
                    endTransaction(false);
                }
                return false;
            }
        }
        String sql = "UPDATE " + getTablaName() + " SET id_usuario = ?, id_libro = ? WHERE ID = ?";
        try {
            int parIndex = 0;
            PreparedStatement sentenciaSQL = con.prepareStatement(sql);
            sentenciaSQL.setInt(++parIndex, prest.getIdUsuario());
            sentenciaSQL.setInt(++parIndex, prest.getIdLibro());
            boolean valido = sentenciaSQL.executeUpdate() > 0;
            if (valido) {
                if (!sameLibro) {
                    valido = l2.devolverCopia() && lDAO.actualizarCopias(l) && lDAO.actualizarCopias(l2);
                }
            }
            if (notTranPrev) {
                return endTransaction(valido);
            }
            return valido;
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.err.println("Ya existe un " + getTablaName().substring(0, getTablaName().length() - 1) + " con la combinación de usuario y libro de \"" + prest.toString() + "\"");
            } else {
                e.printStackTrace();
            }
            if (notTranPrev) {
                endTransaction(false);
            }
            return false;
        }
    }

    @Override 
    protected boolean delete(PrestamoDTO p){
        boolean notTranPrev = false;
        try {
            notTranPrev = con.getAutoCommit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (notTranPrev) {
            if (!beginTransaction()) {
                return false;
            }
        }
        LibroDAOtran lDAO = new LibroDAOtran(con);
        LibroDTO l = lDAO.findById(p.getIdLibro());
        if (l != null && !l.devolverCopia()) {
            System.out.println("Error al devolver el libro \"" + l.toString());
            if (notTranPrev) {
                endTransaction(false);
            }
            return false;
        }
        boolean valid = lDAO.actualizarCopias(l) && super.delete(p);
        if (notTranPrev) {
            return endTransaction(valid);
        }
        return valid;
    }

    public PrestamoDTO[] findByUsuario(UsuarioDTO us) {
        if (us.getId() < 0) {
            return null;
        }
        PrestamoDTO[] prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM " + getTablaName() + " WHERE id_usuario = " + us.getId());
            prestamos = parseRs(rs);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public Integer[] getIdsByUsuario(UsuarioDTO us) {
        if (!(new UsuarioDAOtran(con).exists(us))) {
            return null;
        }
        List<Integer> prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT id FROM " + getTablaName() + " WHERE id_usuario = " + us.getId());
            if (rs.next()) {
                prestamos = new ArrayList<>();
                do {
                    prestamos.add(rs.getInt("id"));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos == null || prestamos.size() == 0 ? null : prestamos.toArray(new Integer[0]);
    }

    public PrestamoDTO[] findByLibro(LibroDTO libro) {
        if (libro.getId() < 0) {
            return null;
        }
        PrestamoDTO[] prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()){
            ResultSet rs = sentenciaSQL.executeQuery("SELECT * FROM " + getTablaName() + " WHERE id_libro = " + libro.getId());
            prestamos = parseRs(rs);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public Integer[] getIdsByLibro(LibroDTO libro) {
        if (!(new LibroDAOtran(con).exists(libro))) {
            return null;
        }
        List<Integer> prestamos = null;
        try (Statement sentenciaSQL = con.createStatement()) {
            ResultSet rs = sentenciaSQL.executeQuery("SELECT id FROM " + getTablaName() + " WHERE id_libro = " + libro.getId());
            if (rs.next()) {
                prestamos = new ArrayList<>();
                do {
                    prestamos.add(rs.getInt("id"));
                } while (rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos == null || prestamos.size() == 0 ? null : prestamos.toArray(new Integer[0]);
    }

    @Override
    public String getTablaName() {
        return "prestamos";
    }

    @Override
    public PrestamoDTO[] parseRs(ResultSet rs) throws SQLException {
        List<PrestamoDTO> prestamos = null;
        if (rs.next()) {
            prestamos = new ArrayList<>();
            do {
                prestamos.add(new PrestamoDTO(
                        rs.getInt("id"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_usuario")
                    ));
            } while (rs.next());
        }
        rs.close();
        return prestamos == null ? null : prestamos.toArray(new PrestamoDTO[0]);
    }

    @Override
    public boolean isBorradoPosible(PrestamoDTO presamo) {
        return presamo != null;
    }
}