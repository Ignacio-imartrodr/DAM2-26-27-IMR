package ADAT.UD1.Texto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Crea un programa que compare dos ficheros de texto e indique si son idénticos
 * y, en
 * caso de no serlo, en que línea y columna tienen el primer caracter distinto
 * 
 * @author Ignacio MR
 */
public class ComparadorFicheros {
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

    private static int compararLineas(String l1, String l2) {
        int length1 = l1.length();
        int length2 = l2.length();
        int smallLength = length1 < length2 ? length1 : length2;
        for (int i = 0; i < smallLength; i++) {
            char c1 = l1.charAt(i);
            char c2 = l2.charAt(i);
            if (Character.compare(c1, c2) != 0) {
                return i;
            }
        }
        if (length1 != length2) {
            return smallLength;
        }
        return -1;
    }

    public static void main(String[] args) {
        File txtFile1 = selectorArchivo(false, true);
        List<String> texto1;
        if (txtFile1 != null) {
            System.out.println("Archivo seleccionado: " + txtFile1.getAbsolutePath());
            texto1 = leerTxt(txtFile1.getAbsolutePath());
            File txtFile2 = selectorArchivo(false, true);
            List<String> texto2;
            if (txtFile2 != null) {
                System.out.println("Archivo seleccionado: " + txtFile2.getAbsolutePath());
                texto2 = leerTxt(txtFile2.getAbsolutePath());
                boolean diferecia = false;
                int nLineaDif = -1;
                int posDif = -1;
                int sizeTxt1 = texto1.size();
                int sizeTxt2 = texto2.size();
                if (sizeTxt1 == sizeTxt2) {
                    for (int i = 0; !diferecia && i < sizeTxt1; i++) {
                        String linea1 = texto1.get(i);
                        String linea2 = texto2.get(i);
                        posDif = compararLineas(linea1, linea2);
                        if (posDif != -1) {
                            nLineaDif = i;
                            diferecia = true;
                        }
                    }
                } else {
                    nLineaDif = (sizeTxt1 > sizeTxt2 ? sizeTxt2 : sizeTxt1);
                    for (int i = 0; i < nLineaDif; i++) {
                        String linea1 = texto1.get(i);
                        String linea2 = texto2.get(i);
                        posDif = compararLineas(linea1, linea2);
                        if (posDif != -1) {
                            nLineaDif = i;
                            diferecia = true;
                        }
                    }
                    if (!diferecia) {
                        diferecia = true;
                        nLineaDif++;
                        posDif = 0;
                    }
                }

                if (diferecia) {
                    System.out.println("Los documentos son diferentes en el carácter " + posDif + " de la línea " + nLineaDif + ".");
                } else {
                    System.out.println("Los documentos son iguales.");
                }
            }
        }
    }
}
