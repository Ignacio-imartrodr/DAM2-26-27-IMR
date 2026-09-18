package Otros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManejarTxt {
    /**
     * Lee un archivo de texto por lineas utilizando un buffer
     * 
     * @param rutaObjetivo ruta al archivo de texto
     * @return Una {@code Lista<String>} conteniendo un {@code String} por línea en
     *         el archivo si todo sale bien.
     *         <p>
     *         En caso de error la lista contiene
     *         {@code "-1"} en la primera posición, la causa del error y el número
     *         asignado al error en última posición.
     *         <p>
     *         - {@code "0"}: Archivo no encontrado
     *         <p>
     *         - {@code "1"}: Error Entrada/Salida
     *         <p>
     *         - {@code "2"}: Error desconocido
     */
    public static List<String> leerTxt(String rutaObjetivo) {
        List<String> error = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(rutaObjetivo))) {
            List<String> txt = new ArrayList<>(in.readAllLines());
            return txt;
        } catch (FileNotFoundException e) {
            error.add("-1");
            error.add("No se encuentra el fichero de la ruta: " + rutaObjetivo);
            error.add("0");
        } catch (IOException e) {
            error.add("-1");
            error.add("Error Entrada/Salida");
            error.add(e.getStackTrace().toString());
            error.add("1");
        } catch (Exception e) {
            error.add("-1");
            error.add("Error desconocido");
            error.add(e.getStackTrace().toString());
            error.add("2");
        }
        return error;
    }

    public static boolean writeInTxt(String rutaObjetivo, String texto, boolean conservar) {
        java.io.File archivo = new java.io.File(rutaObjetivo);
        if (!archivo.getParentFile().exists()) {
            archivo.getParentFile().mkdirs();
        }
        if (texto == null) {
            texto = "";
        }
        if (conservar) {
            List<String> oldTxt = leerTxt(rutaObjetivo);
            if (oldTxt.size() > 0 && oldTxt.getFirst().equals("-1")) {
                return false;
            }
            if (!texto.isEmpty()) {
                oldTxt.add(texto);
            }
            return writeInTxt(rutaObjetivo, oldTxt, false);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(texto);
            writer.flush();
            return true;
        } catch (Exception e) {
            System.err.println("Hubo un error escribiendo en el archivo: " + rutaObjetivo);
            return false;
        }
    }
    public static boolean writeInTxt(String rutaObjetivo, List<String> lineas, boolean conservar) {
        String texto = "";
        if (lineas != null) {
            for (int i = 0; i < lineas.size() - 1; i++) {
                texto = texto + lineas.get(i) + "\n";
            }
            texto = texto + lineas.getLast();
        }
        return writeInTxt(rutaObjetivo, texto, conservar);
    }
}
