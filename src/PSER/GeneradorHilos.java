package PSER;

public class GeneradorHilos {
    public void main(String[] args) {
        final int CANT_HILOS = 10;
        Hilo h = null;
        for (int i = 0; i < CANT_HILOS; i++) {
            h = new Hilo("hilo" + i);
            h.start();
        }
        while (Thread.activeCount() > 1); //espera a que solo quede el hilo main
        System.out.println("Fin del programa");
    }
}
/*// Esta es la clase hilo
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
            }*//*
        }
    }
}*/

