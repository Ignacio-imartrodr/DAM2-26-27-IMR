package ADAT.UD1.Binarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CifradoBinarioSamu {
    public static void main(String[] args) {
        File origen = new File("src\\ADAT\\ud1\\flujosBinarios\\FirmasHexadecimalesEncriptado.java");

        File destino = new File("src\\ADAT\\ud1\\flujosBinarios\\FirmasHexadecimalesDesencriptado.java");

        try (FileInputStream in = new FileInputStream(origen);
                FileOutputStream out = new FileOutputStream(destino);) {

            byte[] ficheroNormal = in.readAllBytes();
            byte[] ficheroEncriptado = new byte[ficheroNormal.length];

            for (int i = 0; i < ficheroNormal.length; i++) {
                ficheroEncriptado[i] = (byte) (~ficheroNormal[i] & 0xFF);
            }

            out.write(ficheroEncriptado);
            out.flush();

        } catch (Exception e) {
            System.out.println("Se ha producido un error " + e.getMessage());
        }
    }
}
