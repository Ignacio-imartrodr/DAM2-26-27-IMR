package adat.ud1.buffers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class ComparaRendimiento {
    private final static String RUTA_FILE = "src\\adat\\ud1\\buffers\\DocPruebas\\FileOutput.bin";
    private final static String RUTA_BUFFER = "src\\adat\\ud1\\buffers\\DocPruebas\\BufferedOutput.bin";

    private static Integer pedirNum(){
        Integer res;
        try (Scanner sc = new Scanner(System.in)) {
            String txt = sc.nextLine();
            for (int i = 0; i < txt.length(); i++) {
                if (!Character.isDigit(txt.charAt(i))) {
                    return null;
                }
            }
            res = Integer.valueOf(txt);
        } catch (Exception e) {
            res = null;
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println("Cantidad de Bytes?");
        Integer numBytes = pedirNum();
        if (numBytes == null) {
            numBytes = 1000000;
            System.out.println("Cantidad de Bytes inválida, utilizando " + numBytes + " bytes en su lugar");
        }

        try {
            File fFile = new File(RUTA_FILE);
            File fBuffer = new File(RUTA_BUFFER);
            if (!fFile.exists()) {
                fFile.getParentFile().mkdirs();
            }
            if (!fBuffer.exists()) {
                fBuffer.getParentFile().mkdirs();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        long recordFile;
        long recordBuffer;

        long inicio = System.nanoTime();
        long fin;
        try (FileOutputStream outA = new FileOutputStream(RUTA_FILE);) {

            for (int i = 0; i < numBytes; i++) {
                outA.write(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fin = System.nanoTime();
        recordFile = (fin - inicio);

        inicio = System.nanoTime();
        try (BufferedOutputStream outB = new BufferedOutputStream(new FileOutputStream(RUTA_BUFFER))) {
            for (int i = 0; i < numBytes; i++) {
                outB.write(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fin = System.nanoTime();
        recordBuffer = (fin - inicio);

        String fullRecord = "Cant: " + numBytes + " bytes\nFileOutput time: " + (recordFile/1000000) + " ms\nBufferedOutput time: " + (recordBuffer/1000000) + " ms";
        System.out.println(fullRecord);
    }
}
