package PSER.ud1.A_examen.entregado;

public class App {
    final static int NUM_HIJOS = 5;
    final static int NUM_PADRES = 1;
    final static int INGRESO_HUCHA = 10;
    final static int MIN_ESPERA = 10;
    final static int MAX_ESPERA = 100;
    final static int NUM_RETIRADAS = 5;

    public static void main(String[] args) {
        Hucha hucha = new Hucha();
        Hilo[] hilos = new Hilo[NUM_HIJOS + NUM_PADRES];

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