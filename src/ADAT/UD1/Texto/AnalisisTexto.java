package adat.ud1.texto;

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
 * Crea un programa que solicite un fichero de texto y cuente el número de
 * caracteres, de vocales, de consonantes, de dígitos y de espacios en blanco.
 * 
 * @author Ignacio MR
 */
public class AnalisisTexto {
    private final static String RUTA_A_ARCHIVOS = "src\\ADAT\\FicherosDatos";
    private static File selectorArchivo(boolean crear, boolean soloTxt) {
        JFileChooser chooser = new JFileChooser(RUTA_A_ARCHIVOS);
        if (soloTxt) {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileFilter() { //Creo un filtro para que solo acepte archivos de texto
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
        File txtFile = selectorArchivo(false, true);
        if (txtFile != null) {
            System.out.println("Archivo seleccionado: " + txtFile.getAbsolutePath());
            List<String> texto = leerTxt(txtFile.getAbsolutePath());
            if (texto.size() <= 0){
                System.out.println("Fichero de texto vacío");
            } else if(texto.getFirst().equals("-1")) {
                for (int i = 1; i < texto.size(); i++) {
                    System.out.println(texto.get(i));
                }
            } else {
                int nChar = 0;
                int nVocal = 0;
                int nCons = 0;
                int nDig = 0;
                int nBlank = 0;
                for (String string : texto) {
                    nChar += string.length();
                    for (int i = 0; i < string.length(); i++) {
                        char letra = string.charAt(i);
                        switch (letra) {
                            case 'a', 'e', 'i', 'o', 'u', 'á', 'é', 'í', 'ó', 'ú':
                                nVocal++;
                                break;
                            case Character.SPACE_SEPARATOR, '\s':
                                nBlank++;
                                break;
                            default:
                                if (Character.isDigit(letra)) {
                                    nDig++;
                                } else if (Character.isLetter(letra)) {
                                    nCons++;
                                }
                                break;
                        }
                    }
                }
                System.out.println("El archivo \"" + txtFile.getName() + "\" contiene:");
                System.out.println(nChar + " carácteres");
                System.out.println(nVocal + " vocales");
                System.out.println(nCons + " consonantes");
                System.out.println(nDig + " dígitos");
                System.out.println(nBlank + " espacios blancos");
            }
        }
    }    
}
