package adat.ud2.biblioteca.daoYdto.usuario;

import adat.ud2.biblioteca.daoYdto.AbstractDTO;

public class UsuarioDTO extends AbstractDTO {
    private String nombre;
    private String correo;
    
    public UsuarioDTO(int id, String nombre, String correo) {
        super(id);
        if (!setNombre(nombre) || !setCorreo(correo)) {
            throw new IllegalArgumentException("El nombre y el correo no pueden ser null o estar vacíos");
        }
    }
    
    public UsuarioDTO(String nombre, String correo) {
        this(-1, nombre, correo);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        this.nombre = nombre;
        return true;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean setCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            return false;
        }
        this.correo = correo;
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioDTO [id= " + id + ", nombre= " + nombre + ", correo= " + correo + "]";
    }
}
