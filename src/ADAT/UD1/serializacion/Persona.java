package adat.ud1.serializacion;
import java.io.Serializable;
import java.time.LocalDate;

public class Persona implements Serializable{
    private static final long serialVersionUID = 1L;

    private String nombre;
    private LocalDate fechaNacimiento;
    private transient String contrasenia; // no se serializa

    public Persona(String nombre, LocalDate fechaNacimiento, String contrasenia) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", contrasenia=" + contrasenia
                + "]";
    }

    
}
