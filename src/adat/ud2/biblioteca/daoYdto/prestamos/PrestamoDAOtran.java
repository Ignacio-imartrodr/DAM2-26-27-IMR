package adat.ud2.biblioteca.daoYdto.prestamos;

import java.sql.Connection;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDTO;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDTO;

public class PrestamoDAOtran extends AbstractDAOtran<PrestamoDTO> {

    public PrestamoDAOtran(Connection con){
        super(con);
    }
    @Override
    public boolean borrar(PrestamoDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrar'");
    }

    @Override
    public boolean insertar(PrestamoDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public boolean actualizar(PrestamoDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public PrestamoDTO find(PrestamoDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public PrestamoDTO[] findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public LibroDTO[] findByUsuario(UsuarioDTO us) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsuario'");
    }

    public UsuarioDTO[] findByLibro(LibroDTO us) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsuario'");
    }
}
