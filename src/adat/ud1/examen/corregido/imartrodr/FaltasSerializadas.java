package adat.ud1.examen.corregido.imartrodr;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * @author Ignacio Martínez Rodríguez
 */
public class FaltasSerializadas {
    public final static String RUTA_CSV = "src\\adat\\ud1\\examen\\DATOS\\consultaxeradorinformes.csv";
    public final static String RUTA_BIN = "src\\adat\\ud1\\examen\\DATOS\\FaltasAsistencia.dat";

    public static void main(String[] args) {
        String[] csv = leerCSV();
        String[] entrada;
        List<FaltaAsistencia> faltas = new ArrayList<>();
        for (int i = 0; i < csv.length; i++) {
            entrada = csv[i].replace("\"", "").split(";");

            if (entrada[FaltaAsistencia.POS_XUS].equalsIgnoreCase("non")) {
                faltas.add(new FaltaAsistencia(entrada));
            }
        }
        writeFaltasAsistencia(faltas, RUTA_BIN);

        System.out.println(Arrays.toString(reedFaltasAsistencia(RUTA_BIN)));
    }

    public static String[] leerCSV() {
        String[] contenido = null;
        List<String> list = null;
        try (var in = new BufferedReader(new FileReader(RUTA_CSV))) {
            list = in.readAllLines();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (list != null) {
            contenido = new String[list.size() - 1];
            for (int i = 0; i < contenido.length; i++) {
                contenido[i] = list.get(i + 1);
            }
        }
        return contenido;
    }
    

    public static FaltaAsistencia[] reedFaltasAsistencia(String ruta) {
        List<FaltaAsistencia> l = new ArrayList<>();
        try (var in = new ObjectInputStream(new FileInputStream(ruta));) {
            while (true) {
                l.add((FaltaAsistencia) in.readObject());
            }
        } catch (EOFException end) {
            //todo bien, terminó de leer
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        FaltaAsistencia[] faltas = new FaltaAsistencia[l.size()];
        for (int i = 0; i < faltas.length; i++) {
            faltas[i] = l.get(i);
        }
        return faltas;
    }
    public static boolean writeFaltasAsistencia(List<FaltaAsistencia> faltas, String ruta) {
        try (var out = new ObjectOutputStream(new FileOutputStream(ruta));) {
            FaltaAsistencia[] f = reedFaltasAsistencia(ruta);
            for (FaltaAsistencia faltaAsistencia : f) {
                out.writeObject(faltaAsistencia);
            }
            for (FaltaAsistencia faltaAsistencia : faltas) {
                out.writeObject(faltaAsistencia);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
