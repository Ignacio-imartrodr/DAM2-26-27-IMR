package adat.ud2.h2bd.biblioteca.daoYdto;

public abstract class AbstractDTO implements Comparable<AbstractDTO>{
    protected int id;
    public abstract int getId();
    
    public AbstractDTO(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id no puede ser null");
        }
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return compareTo(getClass().cast(obj)) == 0;
    }
    
    public static <T extends AbstractDTO> int compare(T o1, T o2){
        return o1.compareTo(o2);
    }
}
