package adat.ud1.examen.corregido.imartrodr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import com.google.gson.Gson;

/**
 * 
 * @author Ignacio Martínez Rodríguez
 */
public class ExportarJsonEmpleados {
    public final static String RUTA_JSON = "DATOS\\empleados.json";
    public static boolean exportarJson(List<Empleado> empleados, String fichero){
        Gson gson = new Gson();
        Empleado[] emp = new Empleado[empleados.size()];
        for (int i = 0; i < emp.length; i++) {
            emp[i] = empleados.get(i);
        }
        String json = gson.toJson(emp, Empleado[].class);
        try (var out = new BufferedWriter(new FileWriter(fichero))){
            out.write(json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        List<Empleado> empleados = List.of(
            new Empleado(1, "Ana García", "Informática", 35000),
            new Empleado(2, "Luis Pérez", "Recursos Humanos", 28000),
            new Empleado(3, "María López", "Informática", 42000));
        System.out.println(exportarJson(empleados, RUTA_JSON));
    }
}
