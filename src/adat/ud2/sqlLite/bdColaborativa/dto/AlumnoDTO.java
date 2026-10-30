package adat.ud2.sqlLite.bdColaborativa.dto;

/**
 * @author Ignacio MR
 * AlumnoDTO
 */
public class AlumnoDTO {
    
    private int id;
    private String nombre;
    private String apellidos;
    private String curso;
    
    // Constructores
    public AlumnoDTO(int id, String nombre, String apellidos, String curso) {
        if (!setNombre(nombre) || !setApellidos(apellidos)) {
            throw new IllegalArgumentException("El nombre y el apellidos no pueden ser null o estar vacíos");
        }
        setCurso(curso);
        this.id = id;
    }

    public AlumnoDTO(int id) {
        this.id = id;
    }

    public AlumnoDTO() {
    }

    // Setters y Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getApellidos() {
        return apellidos;
    }

    public boolean setApellidos(String apellidos) {
        if (apellidos == null || apellidos.isBlank()) {
            return false;
        }
        this.apellidos = apellidos;
        return true;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        if (curso == null || curso.isBlank()) {
            this.curso = null;
        }
        this.curso = curso;
    }
    
}
