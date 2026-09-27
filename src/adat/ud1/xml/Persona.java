package adat.ud1.xml;

import java.io.Serializable;

public class Persona implements Serializable {
    static final long serialVersionUID = 5L;
    private String nombre;
    private int edad;
    
    public Persona(String nombre, int edad) {
        setNombre(nombre);
        setEdad(edad);
    }
    
    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre + ", edad: " + edad;
    }
}
