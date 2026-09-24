package PSER;

import java.util.Random;

public class ContadorConHilos {
    public static void main(String[] args) {
        final int CANT_HILOS = 3;
        Random rnd = new Random();
        int[] contador = new int[1];
        int esperado = 0;
        HiloIncrementador[] hilos = new HiloIncrementador[CANT_HILOS];
        for (int i = 0; i < CANT_HILOS; i++) {
            int cant = rnd.nextInt(1,10);
            esperado += cant;
            hilos[i] = new HiloIncrementador("hilo " + i + " - " + cant, cant, contador);
            hilos[i].start();
        }
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (Exception e) {}
        }
        System.out.println("Contador final = " + contador[0]);
        System.out.println("Contador esperado: " + esperado);
    }
}

/**
 * HiloIncrementador
 */
class HiloIncrementador extends Thread {
    int[] contador;
    int cantRnd;
    public HiloIncrementador(String nombre, int cant, int[] contador) {
        super(nombre);
        this.contador = contador;
        this.cantRnd = cant;
    }

    @Override 
    public void run() {
        for (int i = 0; i < cantRnd; i++) {
            System.out.println(getName() + " > " + contador[0] + " + 1");
            contador[0]++;
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }*/
        }
    }
}
