package adat.ud2.biblioteca.daoYdto;

public abstract class AbstractDTO implements Comparable<AbstractDTO>{
    protected int id;
    
    protected AbstractDTO(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser null");
        }
        this.id = id;
    }

    public abstract int getId();

    @Override
    public boolean equals(Object obj) {
        AbstractDTO o;
        if (obj instanceof AbstractDTO) {
            o = (AbstractDTO) obj;
        } else {
            return super.equals(obj);
        }
        return this.compareTo(o) == 0;
    }
}
