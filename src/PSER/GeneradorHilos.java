package PSER;

public class GeneradorHilos {
    public static void main(String[] args) {
        final int CANT_HILOS = 10;
        for (int i = 0; i < CANT_HILOS; i++) {
            Hilo h = new Hilo("hilo" + i);
            h.start();
        }
    }
}

     class Hilo extends Thread {
        public Hilo(String nombre) {
            super(nombre);
        }

        @Override 
        public void run() {
            final int CANT_IT = 10;
            for (int i = 0; i < CANT_IT; i++) {
                System.out.println(getName() + " > " + i);
                /*try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }*/
            }
        }
    }

