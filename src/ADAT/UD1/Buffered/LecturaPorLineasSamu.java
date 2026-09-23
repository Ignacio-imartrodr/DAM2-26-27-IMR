package ADAT.UD1.Buffered;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class LecturaPorLineasSamu {
    public static void main(String[] args) {
        File origen = new File("DATOS\\alumnos.txt");
        String palabraClave = "A";

        try (BufferedReader in = new BufferedReader(new FileReader(origen))) {
            List<String> lista = in.readAllLines();

            for (String cadena : lista) {

                if (cadena.contains(palabraClave)) {
                    System.out.println(cadena);
                }
            }

        } catch (Exception e) {
            System.out.println("Se ha producido un error" + e.getMessage());
        }
    }
}
