package PSER.examenes.ud1.corregido;

public class App {
    final static int NUM_HIJOS = 5;
    final static int NUM_PADRES = 1;
    final static int INGRESO_HUCHA = 12;
    final static int MIN_ESPERA = 900;
    final static int MAX_ESPERA = 1500;
    final static int NUM_RETIRADAS = 7;
    final static int TIEMPO_ENTRE_SEMANAS = 1000;
    static boolean fin = false;


    public static void main(String[] args) {
        final Semana SEMANA = new Semana();
        final Hucha HUCHA = new Hucha();
        final Familiar[] HILOS = new Familiar[NUM_HIJOS + NUM_PADRES];

        for (int i = 0; i < HILOS.length; i++) {
            if(i < NUM_HIJOS) {
                HILOS[i] = new Hijo("h" + i, HUCHA, MIN_ESPERA, MAX_ESPERA, NUM_RETIRADAS, SEMANA);
            } else {
                HILOS[i] = new Progenitor("p" + (i - NUM_HIJOS), HUCHA, INGRESO_HUCHA, SEMANA);
            }
            HILOS[i].start();
        }

        final Thread TIEMPO = Thread.startVirtualThread(new Runnable() {
            @Override
            public void run() {
                while (!fin && !Thread.interrupted()) {
                    try {
                        Thread.sleep(TIEMPO_ENTRE_SEMANAS);
                    } catch (InterruptedException e) {
                        fin = true;
                    }
                    if (!fin && !Thread.interrupted()) {
                        System.out.println("Tiempo > Nueva semana");
                        SEMANA.incrementar();
                    }
                }
            }
        });

        for (int i = 0; i < HILOS.length; i++) {
            if (i < NUM_HIJOS) {
                try {
                    HILOS[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            } else {
                HILOS[i].interrupt();
            }
        }
        fin = true;
        TIEMPO.interrupt();
        System.out.println("En la hucha quedaron: " + HUCHA.getCantidad());
    }
}