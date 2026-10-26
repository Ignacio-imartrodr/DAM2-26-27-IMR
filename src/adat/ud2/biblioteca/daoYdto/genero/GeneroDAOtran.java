package adat.ud2.biblioteca.daoYdto.genero;

import java.sql.Connection;

import adat.ud2.biblioteca.daoYdto.AbstractDAOtran;

public class GeneroDAOtran extends AbstractDAOtran<GeneroDTO> {

    public GeneroDAOtran(Connection con) {
        super(con);
    }

    @Override
    public boolean borrar(GeneroDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'borrar'");
    }

    @Override
    public boolean insertar(GeneroDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public boolean actualizar(GeneroDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public GeneroDTO find(GeneroDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public GeneroDTO[] findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
