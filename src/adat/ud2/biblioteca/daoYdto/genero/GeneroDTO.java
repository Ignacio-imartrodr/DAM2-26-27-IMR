package adat.ud2.biblioteca.daoYdto.genero;

import adat.ud2.biblioteca.daoYdto.AbstractDTO;

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
}
