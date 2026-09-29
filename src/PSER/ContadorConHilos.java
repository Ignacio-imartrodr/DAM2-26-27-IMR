package PSER;

import java.util.Random;

public class ContadorConHilos {
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

/**
 * HiloIncrementador
 */
class HiloIncrementador extends Thread {
    Contador contador;
    int nAumentos;

    public HiloIncrementador(String nombre, int nAumentos, Contador contador) {
        super(nombre);
        this.nAumentos = nAumentos;
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < nAumentos; i++) {
            synchronized(contador){
                System.out.println(getName() + " > " + contador.getContador() + " + 1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                }
                contador.incrementar();
            }
        }
    }
}

/**
 * Contador
 */
class Contador {
    private int contador;

    public Contador(){
        this.contador = 0;
    }

    public synchronized void incrementar(){
        contador++;
    }
    public void reset(){
        contador = 0;
    }
    public int getContador(){
        return contador;
    }
}
