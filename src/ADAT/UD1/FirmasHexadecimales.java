package ADAT.UD1;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * Más allá de su extensión (.pdf, .jpg, .png, .doc, etc.), algunos formatos de
 * archivo pueden reconocerse por los valores de sus primeros y últimos bytes,
 * en lo que se conoce como firmas hexadecimales o "números mágicos". En la
 * siguiente página puedes consultar algunos de estas firmas:
 * https://garykessler.net/library/file_sigs.html
 * Elabora un programa que permita seleccionar archivos de al menos 3
 * extensiones distintas y que comprueba si la firma coincide con su extensión
 * 
 * @author Ignacio MR
 * 
 */
public class FirmasHexadecimales {
    private final static String FIRMA_PDF_INI = "25 50 44 46";
    private final static String FIRMA_PDF_FIN = "25 25 45 4F 46";
    private final static String FIRMA_ODT = "50 4B 03 04";
    private final static String FIRMA_ZIP = "57 69 6E 5A 69 70";

    public static void main(String[] args) {
        System.out.println("Selecciona un archivo  \"*.pdf\",\"*.odt\" o \"*.zip\"");
        File file = selectorArchivo(false);

    }

    private static File selectorArchivo(boolean crear) {
        JFileChooser chooser = new JFileChooser();
        int selector = crear ? chooser.showSaveDialog(null) : chooser.showOpenDialog(null);
        File directorio = null;
        if (selector == JFileChooser.APPROVE_OPTION) {
            directorio = chooser.getSelectedFile();
        } else {
            System.out.println("Archivo no seleccionado");
        }
        return directorio;
    }
}
