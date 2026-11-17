package PSER.pescadores;

//import java.util.Random;

/**
 * Pescador
 */
public class Pescador extends Thread {
    //private static Random rnd = new Random();
    Lector lector;

    public Pescador(String nombre, Lector lector) {
        super(nombre);
        this.lector = lector;

    }

    @Override
    public void run() {
        /*try {
            Thread.sleep(rnd.nextLong(100,1000));
        } catch (InterruptedException ex) {
            System.out.println("Error mientras pensaba");
        }*/
        lector.leer(this);
    }
    public void hablar(String frase) {
        System.out.println(getName() + " > " + frase);
    }
}