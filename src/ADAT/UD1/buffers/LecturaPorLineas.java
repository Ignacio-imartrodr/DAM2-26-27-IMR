package adat.ud1.buffers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
/**
 * Lee un fichero de texto con BufferedReader.readLine() y muestra por pantalla
 * solo las líneas que contengan una palabra clave introducida por el usuario.
 * 
 * @author Ignacio MR
 */
public class LecturaPorLineas {
    private final static String RUTA_A_ARCHIVOS = "src\\ADAT\\FicherosDatos";
    private static File selectorArchivo(boolean crear, boolean soloTxt) {
        JFileChooser chooser = new JFileChooser(RUTA_A_ARCHIVOS);
        if (soloTxt) {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileFilter() { // Creo un filtro para que solo acepte archivos de texto
                @Override
                public boolean accept(File f) {
                    String name = f.getName();
                    int posExt = name.lastIndexOf(".");
                    if (posExt != -1) {
                        name = name.substring(posExt);
                        return name.equals(".txt");
                    } else {
                        return true;
                    }
                }

                @Override
                public String getDescription() {
                    return "TEXT files";
                }

            });
        }
        int selector = crear ? chooser.showSaveDialog(null) : chooser.showOpenDialog(null);
        File directorio = null;
        if (selector == JFileChooser.APPROVE_OPTION) {
            directorio = chooser.getSelectedFile();
        } else {
            System.out.println("Archivo no seleccionado");
        }
        return directorio;
    }
    private static List<String> escogerLineas(String rutaObjetivo, String clave){
        List<String> error = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(rutaObjetivo))) {
            List<String> txt = in.readAllLines();
            for (String linea : txt) {
                if (clave == null || clave.isEmpty()) {
                    error.add(linea);
                } else if (linea.contains(clave)) {
                    error.add(linea);
                }
            }
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
    private static String pedirClave(){
        String res;
        try (Scanner sc = new Scanner(System.in)) {
            res = sc.nextLine();
            if (res.isEmpty()) {
                res = null;
            }
        } catch (Exception e) {
            res = null;
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println("Escoge el archivo que filtrar:");
        File file = selectorArchivo(false, true);
        System.out.println("Que clave quieres buscar?");
        String clave = pedirClave();
        List<String> txtFiltrado = escogerLineas(file.getAbsolutePath(), clave);
        if (txtFiltrado.size() > 0) {
            if (txtFiltrado.getFirst().equals("-1")) {
                for (int i = 1; i < txtFiltrado.size() - 1; i++) {
                    System.out.println(txtFiltrado.get(i));
                }
            } else {
                System.out.println("Texto filtrado:");
                for (String linea : txtFiltrado) {
                    System.out.println(linea);
                }
            }
        } else {
            System.out.println("El archivo no contenía la clave");
        }
    }
}
