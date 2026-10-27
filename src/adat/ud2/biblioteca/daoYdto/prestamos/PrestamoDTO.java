package adat.ud2.biblioteca.daoYdto.prestamos;

import adat.ud2.biblioteca.daoYdto.AbstractDTO;

public class PrestamoDTO extends AbstractDTO {
    private int idUsuario;
    private int idLibro;
    
    public PrestamoDTO(int id, int idUsuario, int idLibro) {
        super(id);
        if (!setIdUsuario(idUsuario) || !setIdLibro(idLibro)) {
            throw new IllegalArgumentException("Los id de usuario y libro deben ser mayores o iguales a 0");
        }
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
    }

    public PrestamoDTO(int idUsuario, int idLibro) {
        this(-1, idUsuario, idLibro);
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

    @Override
    public int compareTo(AbstractDTO o) {
        if (o instanceof PrestamoDTO) {
            PrestamoDTO p = (PrestamoDTO) o;
            int compUsuario = Integer.compare(this.getIdUsuario(), p.getIdUsuario());
            if (compUsuario != 0) {
                return compUsuario;
            }

            int compLibro = Integer.compare(this.getIdLibro(), p.getIdLibro());
            if (compLibro != 0) {
                return compLibro;
            }
        }
        return Integer.compare(this.getId(), o.getId());
    }
}
