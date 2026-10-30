package adat.ud2.h2bd.CRUD_DAO_DTO_Profesor_Scarbmora.CRUD_DAO_DTO_Profesor_Scarbmora;

import java.time.LocalDate;

public class ProfesorDTO {
    private int id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    //private List<String> materiasImparte;
    //private List<String> cursosImparte;
    private  String direccion;
    private  String telefono;


    public ProfesorDTO( int id,  String nombre, String apellidos, LocalDate fechaNacimiento/*, List<String> materiasImparte,
            List<String> cursosImparte*/, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        //this.materiasImparte = materiasImparte;
        //this.cursosImparte = cursosImparte;
        this.direccion = direccion;
        this.telefono = telefono;
    }


    public ProfesorDTO(String nombre, String apellidos, LocalDate fechaNacimiento/*, List<String> materiasImparte,
            List<String> cursosImparte*/, String direccion, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        //this.materiasImparte = materiasImparte;
        //this.cursosImparte = cursosImparte;
        this.direccion = direccion;
        this.telefono = telefono;
    }


    public ProfesorDTO(int id) {
        this.id = id;
    } 


    public ProfesorDTO() {
    }


     public int getId() {
        return id;
    } 


    public String getNombre() {
        return nombre;
    }


    public String getApellidos() {
        return apellidos;
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }


   /*  public List<String> getMateriasImparte() {
        return materiasImparte;
    }


    public List<String> getCursosImparte() {
        return cursosImparte;
    } 

*/
    public String getDireccion() {
        return direccion;
    }


    public String getTelefono() {
        return telefono;
    }


    @Override
    public String toString() {
        return "ProfesorDTO ["+/* id=" + id + ",  */"nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento="
                + fechaNacimiento + /* ", materiasImparte=" + materiasImparte + ", cursosImparte=" + cursosImparte
                +  */", direccion=" + direccion + ", telefono=" + telefono + "]";
    }

    

}
