package PSER.oficina;

import java.util.Random;

public class AppOficina {
    final static int N_PERSONAS = 10;

    public static void main(String[] args) {
        Oficina oficina = new Oficina();
        Personal[] hilos = new Personal[N_PERSONAS];
        Random rd = new Random();
        int nJefe = rd.nextInt(3, N_PERSONAS);

        for (int i = 0, j = 1; i < N_PERSONAS; i++) {
            if (i == nJefe) {
                hilos[i] = new Jefe("j", oficina);
            } else {
                hilos[i] = new Empleado("e" + j++, oficina);
            }
            hilos[i].start();
        }
    }
}