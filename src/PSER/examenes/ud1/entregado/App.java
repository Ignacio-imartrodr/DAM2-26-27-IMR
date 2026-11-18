package PSER.examenes.ud1.entregado;

public class App {
    final static int NUM_HIJOS = 5;
    final static int INGRESO_HUCHA = 10;
    final static int MIN_ESPERA = 10;
    final static int MAX_ESPERA = 100;
    final static int NUM_RETIRADAS = 10;

    public static void main(String[] args) {
        Hucha hucha = new Hucha();
        Hilo[] hilos = new Hilo[NUM_HIJOS + 1];

        for (int i = 0; i < NUM_HIJOS + 1; i++) {
            if(i < NUM_HIJOS) {
                hilos[i] = new Hijo("h" + i, hucha, MIN_ESPERA, MAX_ESPERA, NUM_RETIRADAS);
            } else {
                hilos[i] = new Padre("p" + (i - NUM_HIJOS), hucha, INGRESO_HUCHA);
            }
            hilos[i].start();
        }
        
        for (int i = 0; i < hilos.length; i++) {
            if (i < NUM_HIJOS) {
                try {
                    hilos[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            } else {
                hilos[i].interrupt();
            }
        }
        System.out.println("En la hucha quedaron: " + hucha.getCantidad());
    }
}