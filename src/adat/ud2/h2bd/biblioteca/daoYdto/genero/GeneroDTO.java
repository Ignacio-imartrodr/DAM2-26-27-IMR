package adat.ud2.h2bd.biblioteca.daoYdto.genero;

import adat.ud2.h2bd.biblioteca.daoYdto.AbstractDTO;

public class GeneroDTO extends AbstractDTO {
    private String nombre;
    
    public GeneroDTO(int id, String nombre) {
        super(id);
        if (!setNombre(nombre)) {
            throw new IllegalArgumentException("El nombre no puede ser null o estar vacío");
        }
    }

    public GeneroDTO(String nombre){
        this(-1, nombre);
    }

    public String getNombre() {
        return nombre.charAt(0) + nombre.substring(1).toLowerCase();
    }
    public boolean setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        this.nombre = nombre.toUpperCase();
        return true;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GeneroDTO [id= " + id + ", nombre= " + nombre + "]";
    }

    @Override
    public int compareTo(AbstractDTO o) {
        if (this == o)
            return 0;
        if (o == null)
            return -1;
        int compId = id < 0 || o.getId() < 0 ? 0 : Integer.compare(id, o.getId()); 
        GeneroDTO g;
        if (getClass() == o.getClass()){
            g = getClass().cast(o);
        } else {
            return compId;
        }
        if (compId != 0) {
            return compId;
        }
        int compNombre = this.nombre.compareTo(g.nombre);
        return compNombre;
    }
}
