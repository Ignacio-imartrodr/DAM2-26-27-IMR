package adat.ud2.biblioteca.daoYdto;

public abstract class AbstractDTO {
    protected int id;
    protected AbstractDTO(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser null");
        }
        this.id = id;
    }
    public abstract int getId();
}
