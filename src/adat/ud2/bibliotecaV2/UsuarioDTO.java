package adat.ud2.bibliotecaV2;

public class UsuarioDTO extends AbstractDTO {
    private int id;
    private String nombre;
    private String correo;
    
    public UsuarioDTO(int id, String nombre, String correo) {
        if (nombre == null || nombre.isBlank() || correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El nombre y el correo no pueden ser null o estar vacíos");
        }
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "UsuarioDTO [id= " + id + ", nombre= " + nombre + ", correo= " + correo + "]";
    }
}