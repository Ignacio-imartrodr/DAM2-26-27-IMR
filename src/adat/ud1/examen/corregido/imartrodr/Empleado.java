package adat.ud1.examen.corregido.imartrodr;

public class Empleado {
    int id;
    String nombre;
    String departamento;
    double salario;

    public Empleado(int id, String nombre, String departamento, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.salario = salario;
    }



    @Override
    public String toString() {
        return "Empleado [id=" + id + ", nombre=" + nombre + ", departamento=" + departamento + ", salario=" + salario
                + "]";
    }
}
