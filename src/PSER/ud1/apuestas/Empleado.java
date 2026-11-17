package PSER.apuestas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Empleado
 */
public class Empleado extends Thread {
    private Random rnd = new Random();
    private List<Tiket> tikets;
    private Tiket[] premiados;
    private Bote bote;
    private int nApuestas;
    private int maxGoles;

    public Empleado(String nombre, Bote bote, int numApuestasEmpleado, int maxGoles) {
        super(nombre);
        this.bote = bote;
        this.nApuestas = numApuestasEmpleado;
        this.maxGoles = maxGoles;
        this.tikets = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int i = 1; i <= nApuestas; i++) {
            try {
                Thread.sleep(rnd.nextLong(100,500));
            } catch (InterruptedException e) {
                System.out.println(getName() + "Error pensando la apuesta");
            }
            tikets.add(bote.apostar(new Resultado(rnd.nextInt(maxGoles), rnd.nextInt(maxGoles)), Math.round((i + rnd.nextDouble()) * 100.0) / 100.0));
        }
    }

    public Tiket[] validarTickets(Tiket[] tiketsGanadores){
        if (premiados == null) {
            List<Tiket> premiados = new ArrayList<>();
            for (Tiket tiket : tiketsGanadores) {
                for (Tiket t : tikets) {
                    if (tiket.getId() == t.getId()) {
                        premiados.add(tiket);
                    }
                }
            }
            this.premiados = premiados == null || premiados.size() == 0 ? null : premiados.toArray(new Tiket[0]);
        }
        return this.premiados;
    }
    public Tiket[] getPremiados() {
        return premiados;
    }
}
