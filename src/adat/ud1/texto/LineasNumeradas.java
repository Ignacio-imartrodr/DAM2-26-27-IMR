package adat.ud1.texto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Crea un programa que lea un fichero de texto y genere una copia en la que
 * cada línea vaya precedida por el número de línea (“1: “, “2: “, 3: “, etc.)
 * 
 * @author Ignacio MR
 */
public class LineasNumeradas {
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
    public static void main(String[] args) {
        File fileIn = selectorArchivo(false, true);
        System.out.println("Archivo seleccionado: " + fileIn.getAbsolutePath());
        String fileName = fileIn.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String rutaOut = fileIn.getParent() + "\\" + fileName.substring(0, (fileName.length() - fileExtension.length())) + "Numerado" + fileExtension;
        File fileOut = new File(rutaOut);
        try (var out = new BufferedWriter(new FileWriter(fileOut));) {
            List<String> txtOg = leerTxt(fileIn.getAbsolutePath());
            int sizeTxt = txtOg.size();
            for (int i = 0; i < sizeTxt; i++) {
                out.write(i + " :" + txtOg.get(i));
                if (i < sizeTxt -1) {
                    out.write("\n");
                }
            }
            out.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
        } catch (IOException IOE) {
            System.out.println("Error de E/S");
        } catch (Exception ex){}
    }
}
