package PSER.contador;

import java.util.Random;

public class AppContadorConHilos {
    public static void main(String[] args) {
        final int N_HILOS = 3;
        Random rnd = new Random();
        int esperado;
        Contador cont = new Contador();
        do {
            esperado = 0;
            cont.reset();
            HiloIncrementador[] hilos = new HiloIncrementador[N_HILOS];
            for (int i = 0; i < N_HILOS; i++) {
                int cant = rnd.nextInt(10,100);
                esperado += cant;
                hilos[i] = new HiloIncrementador("hilo " + i + " - " + cant, cant, cont);
                hilos[i].start();
            }
            for (Thread hilo : hilos) {
                try {
                    hilo.join();
                } catch (Exception e) {}
            }
            System.out.println("Contador final = " + cont.getContador());
            System.out.println("Contador esperado: " + esperado);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
        } while (cont.getContador() == esperado);
        System.out.println("El programa Falló!");
    }
}
