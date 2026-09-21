package ADAT.ud1.flujosTexto;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class LineasNumeradas {
    public static void main(String[] args) {
        File origen = new File("DATOS\\alumnos.txt");
        File destino = new File(
                origen.getAbsolutePath().substring(0, origen.getAbsolutePath().lastIndexOf(".")) + "Copia.txt");

        try (FileReader in = new FileReader(origen);
                FileWriter out = new FileWriter(destino)) {

            List<String> texto = in.readAllLines();

            for (int i = 0; i < texto.size(); i++) {
                out.write(i + 1 + ":" + texto.get(i));
                if (i + 1 != texto.size()) {
                    out.write("\n");
                }
            }

        } catch (Exception e) {
            System.out.println("Se ha producido un error " + e.getMessage());
        }
    }
}
