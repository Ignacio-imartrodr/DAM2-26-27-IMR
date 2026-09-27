package adat.ud1.binarios;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

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
    private final static String RUTA_A_ARCHIVOS = "D:\\imartrodr\\A-DAT\\DAM2-26-27-IMR\\src\\ADAT\\UD1\\DocFirmas";
    private final static int[] FIRMA_PDF = new  int[] {37, 80, 68, 70}; //{0x25, 0x50, 0x44, 0x46};
    private final static int[] FIRMA_ODT = new  int[] {80, 75, 3, 4}; //{0x50, 0x4B, 0x03, 0x04};           Version Hexadecimal
    private final static int[] FIRMA_ZIP_CON_CONT = new  int[] {80, 75, 3, 4, 20, 0}; //{0x57, 0x69, 0x6E, 0x5A, 0x69, 0x70};
    private final static int[] FIRMA_ZIP_VACIA = new  int[] {80, 75, 5, 6, 0, 0};

    public static void main(String[] args) {
        System.out.println("Selecciona un archivo  \"*.pdf\",\"*.odt\" o \"*.zip\"");
        File file = selectorArchivo(false);
        System.out.println("Archivo seleccionado: " + file.getAbsolutePath());
        String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
        switch (extension) {
            case "pdf":
                try (FileInputStream in = new FileInputStream(file)) {
                    byte[] firma = in.readNBytes(FIRMA_PDF.length);
                    boolean correcto = true;

                    for (int i = 0; i < firma.length && correcto; i++) {
                        if (FIRMA_PDF[i] != firma[i]) {
                            correcto = false;
                        }
                    }
                    System.out.println(correcto ? "El archivo es un pdf" : "El archivo NO es un pdf");
                    System.out.println("Firma archivo: " + Arrays.toString(firma));
                    System.out.println("Firma esperada: " + Arrays.toString(FIRMA_PDF));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
                
            case "odt":
                try (FileInputStream in = new FileInputStream(file)) {
                    byte[] firma = in.readNBytes(FIRMA_ODT.length);
                    boolean correcto = true;

                    for (int i = 0; i < firma.length && correcto; i++) {
                        if (FIRMA_ODT[i] != firma[i]) {
                            correcto = false;
                        }
                    }
                    
                    System.out.println(correcto ? "El archivo es un odt" : "El archivo NO es un odt");
                    System.out.println("Firma archivo: " + Arrays.toString(firma));
                    System.out.println("Firma esperada: " + Arrays.toString(FIRMA_ODT));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            
            case "zip":
                try (FileInputStream in = new FileInputStream(file)) {
                    byte[] firma = in.readNBytes(FIRMA_ZIP_CON_CONT.length);
                    boolean correcto = true;

                    for (int i = 0; i < firma.length && correcto; i++) {
                        if (FIRMA_ZIP_CON_CONT[i] != firma[i]) {
                            correcto = false;
                        }
                    }
                    if (!correcto) {
                        correcto = true;
                        for (int i = 0; i < firma.length && correcto; i++) {
                            if (FIRMA_ZIP_VACIA[i] != firma[i]) {
                                correcto = false;
                            }
                        }
                    }
                    
                    System.out.println(correcto ? "El archivo es un zip" : "El archivo NO es un zip");
                    System.out.println("Firma archivo: " + Arrays.toString(firma));
                    System.out.println("Firma con contenido: " + Arrays.toString(FIRMA_ZIP_CON_CONT));
                    System.out.println("Firma vacía: " + Arrays.toString(FIRMA_ZIP_VACIA));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            default:
                System.out.println("Extensión no soportada");
                break;
        }
    }

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
}
