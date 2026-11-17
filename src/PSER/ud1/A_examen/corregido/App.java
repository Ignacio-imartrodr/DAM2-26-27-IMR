package PSER.ud1.A_examen.corregido;

public class App {
    final static int NUM_HIJOS = 5;
    final static int NUM_PADRES = 2;
    final static int INGRESO_HUCHA = 10;
    final static int MIN_ESPERA = 10;
    final static int MAX_ESPERA = 100;
    final static int NUM_RETIRADAS = 10;


    public static void main(String[] args) {
        Semana semana = new Semana();
        Hucha hucha = new Hucha(semana);
        Familiar[] hilos = new Familiar[NUM_HIJOS + NUM_PADRES];

        for (int i = 0; i < hilos.length; i++) {
            if(i < NUM_HIJOS) {
                hilos[i] = new Hijo("h" + i, hucha, MIN_ESPERA, MAX_ESPERA, NUM_RETIRADAS, semana);
            } else {
                hilos[i] = new Progenitor("p" + (i - NUM_HIJOS), hucha, INGRESO_HUCHA, semana);
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