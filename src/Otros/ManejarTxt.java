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
    public static List<String> leerTxt(String rutaObjetivo){
        List<String> error = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(rutaObjetivo))) {
            List<String> txt =  new ArrayList<>(in.readAllLines());
            return txt;
        } catch (FileNotFoundException e) {
            error.add("-1");
            error.add("No se encuentra el fichero");
        } catch (IOException e) {
            error.add("-1");
            error.add("Error Entrada/Salida");
            error.add(e.getStackTrace().toString());
        } catch (Exception e) {
            error.add("-1");
            error.add("Error desconocido: ");
            error.add(e.getStackTrace().toString());
        }
        return error;
    }
    public static boolean writeInTxt(String rutaObjetivo, String texto){
        java.io.File archivo = new java.io.File(rutaObjetivo);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(texto);
            writer.flush();
            return true;
        } catch (Exception e) {
            System.err.println("Hubo un error escribiendo en el archivo");
            return false;
        }
    }
}
