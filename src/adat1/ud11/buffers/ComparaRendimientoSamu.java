package adat.ud1.buffers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class ComparaRendimientoSamu {
    public static void main(String[] args) {
        int numeroBytes = 1000000;

        try (FileOutputStream out = new FileOutputStream("FileOutput.bin");) {

            long inicio = System.nanoTime();
            for (int i = 0; i < numeroBytes; i++) {
                out.write(1);

            }
            long fin = System.nanoTime();
            System.out.println("FileOutput " + (fin - inicio));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedOutputStream outB = new BufferedOutputStream(new FileOutputStream("BufferedOutput.bin"))) {
            long inicio = System.nanoTime();
            for (int i = 0; i < numeroBytes; i++) {
                outB.write(1);

            }
            outB.flush();
            long fin = System.nanoTime();
            System.out.println("BufferedOutput " + (fin - inicio));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
