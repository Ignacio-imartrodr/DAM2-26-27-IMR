package PSER.elecciones;

import java.util.Random;

/**
 * Votante
 */
public class Votante extends Thread {
    static Random rnd = new Random();
    Urna urna;

    public Votante(String nombre, Urna urna) {
        super(nombre);
        this.urna = urna;

    }

    @Override
    public void run() {
        try {
            Thread.sleep(rnd.nextLong(100));
        } catch (InterruptedException ex) {
            System.out.println("Error mientras pensaba");
        }
        int partido = escogerVoto();
        System.out.println(getName() + " > Partido " + urna.getNombrePartido(partido) + " + 1");
        urna.votar(partido);
    }

    private int escogerVoto() {
        int partido = rnd.nextInt(urna.getNumPartidos());// urna.getNumPartidos() - 1;
        return partido;
    }
}