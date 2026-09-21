package ADAT.UD1.Binarios;

import java.io.FileInputStream;

/**
 * @author Samuel
 */

public class FirmasHexadecimalesSamu {
    public static void main(String[] args) {

        String origen = "DATOS\\Participación.txt";

        try (FileInputStream in = new FileInputStream(origen);) {

            switch (origen.substring(origen.lastIndexOf(".") + 1)) {
                case "pdf":

                    byte[] firma = in.readNBytes(4);
                    int[] numerosMagicos = { 0x25, 0x50, 0x44, 0x46 };
                    boolean continuar = true;

                    for (int i = 0; i < firma.length && continuar; i++) {
                        if (numerosMagicos[i] != (firma[i] & 0xFF)) {
                            continuar = false;
                        }
                    }
                    System.out
                            .println(continuar ? "El archivo se trata de un pdf" : "El archivo no se trata de un pdf");
                    break;
                case "png":
                    firma = in.readNBytes(4);
                    numerosMagicos = new int[] { 0x89, 0x50, 0x4E, 0x47 };
                    continuar = true;

                    for (int i = 0; i < firma.length && continuar; i++) {
                        if (numerosMagicos[i] != (firma[i] & 0xFF)) {
                            continuar = false;
                        }
                    }
                    System.out
                            .println(continuar ? "El archivo se trata de un png" : "El archivo no se trata de un png");
                    break;

                case "jpg":
                    firma = in.readNBytes(2);
                    numerosMagicos = new int[] { 0xFF, 0xD8 };
                    continuar = true;

                    for (int i = 0; i < firma.length && continuar; i++) {
                        if (numerosMagicos[i] != (firma[i] & 0xFF)) {
                            continuar = false;
                        }
                    }
                    System.out
                            .println(continuar ? "El archivo se trata de un jpg" : "El archivo no se trata de un jpg");
                    break;
                default:
                    System.out.println("Extensión no soportada");
                    break;
            }

        } catch (Exception e) {
            System.out.println("Se ha producido un error" + e.getMessage());
        }
    }
}
