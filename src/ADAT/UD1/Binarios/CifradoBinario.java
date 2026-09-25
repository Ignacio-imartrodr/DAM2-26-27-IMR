package adat.ud1.binarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;

/**
 * Crea un programa que cifre un fichero binario invirtiendo los valores de sus
 * bits para hacerlo ilegible.
 * Comprueba que aplicando proceso de nuevo el fichero vuelve a ser legible.
 * 
 * @author Ignacio MR
 */
public class CifradoBinario {
    private final static String RUTA_A_ARCHIVOS = "D:\\imartrodr\\A-DAT\\DAM2-26-27-IMR\\src\\ADAT\\UD1\\Serialización";
    private static File selectorArchivo(boolean crear) {
        JFileChooser chooser = new JFileChooser(RUTA_A_ARCHIVOS);
        int selector = crear ? chooser.showSaveDialog(null) : chooser.showOpenDialog(null);
        File directorio = null;
        if (selector == JFileChooser.APPROVE_OPTION) {
            directorio = chooser.getSelectedFile();
        } else {
            System.out.println("Archivo no seleccionado");
        }
        return directorio;
    }
    public static void main(String[] args) {
        File fileIn = selectorArchivo(false);
        System.out.println("Archivo seleccionado: " + fileIn.getAbsolutePath());
        String fileName = fileIn.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String rutaOut = fileIn.getParent() + "\\" + fileName.substring(0, (fileName.length() - fileExtension.length()));
        if (rutaOut.endsWith("Cifrado")) {
            rutaOut = rutaOut.substring(0, (rutaOut.length() - "Cifrado".length())) + "Descifrado" + fileExtension;
        } else if (rutaOut.endsWith("Descifrado")) {
            rutaOut = rutaOut.substring(0, (rutaOut.length() - "Descifrado".length())) + "Cifrado" + fileExtension;
        } else {
            rutaOut += "Cifrado" + fileExtension;
        }
        
        File fileOut = new File(rutaOut);
        try (FileInputStream in = new FileInputStream(fileIn);
            FileOutputStream out = new FileOutputStream(fileOut);) {
            byte[] archivo = in.readAllBytes();
            byte[] cifrado = new byte[archivo.length];
            for (int i = 0; i < archivo.length; i++) {
                cifrado[i] = (byte) ~archivo[i];
            }
            out.write(cifrado);
            out.flush();
            System.out.println("Cifrado completo");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
