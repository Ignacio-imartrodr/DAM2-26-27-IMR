package PSER.pescadores;

import java.io.File;
import java.io.FileNotFoundException;

public class AppConversacion {
    final static int N_PERSONAS = 2;
    final static File CONV = new File("src\\PSER\\pescadores\\conversacion.txt");

    public static void main(String[] args) {
        Lector lector;
        try {
            lector = new Lector(CONV);
            Pescador[] hilos = new Pescador[N_PERSONAS];

            for (int i = 0; i < N_PERSONAS; i++) {
                hilos[i] = new Pescador("p" + i, lector);
                hilos[i].start();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
