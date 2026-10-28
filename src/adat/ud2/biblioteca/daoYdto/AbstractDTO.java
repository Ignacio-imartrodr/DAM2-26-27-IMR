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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractDTO other = (AbstractDTO) obj;
        return compareTo(other) == 0;
    }

}
