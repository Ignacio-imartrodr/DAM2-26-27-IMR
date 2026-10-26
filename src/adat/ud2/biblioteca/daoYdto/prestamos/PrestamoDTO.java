package adat.ud2.biblioteca.daoYdto.prestamos;

import adat.ud2.biblioteca.daoYdto.AbstractDTO;

public class PrestamoDTO extends AbstractDTO {
    private int id;
    private int idUsuario;
    private int idLibro;
    
    public PrestamoDTO(int id, int idUsuario, int idLibro) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public boolean setIdUsuario(int idUsuario) {
        if (idUsuario < 0) {
            return false;
        }
        this.idUsuario = idUsuario;
        return true;
    }
    public int getIdLibro() {
        return idLibro;
    }
    public boolean setIdLibro(int idLibro) {
        if (idLibro < 0) {
            return false;
        }
        this.idLibro = idLibro;
        return true;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PrestamoDTO [id=" + id + ", idUsuario=" + idUsuario + ", idLibro=" + idLibro + "]";
    }
}
