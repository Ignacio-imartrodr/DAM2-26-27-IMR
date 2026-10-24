package adat.ud2.biblioteca;

public class UsuarioDTO {
    private int id;
    private String nombre;
    
    public UsuarioDTO(int id, String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser null o estar vacío");
        }
        this.id = id;
        this.nombre = nombre;
    }
    
    public UsuarioDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
