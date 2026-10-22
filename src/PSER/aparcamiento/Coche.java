package PSER.aparcamiento;

import java.util.Random;

/**
 * Coche
 */
class Coche extends Thread {
    static Random rnd = new Random();
    private Parking parking;

    public Coche(String nombre, Parking parking) {
        super(nombre);
        this.parking = parking;
    }

    /*public void aparcar(){
        int plaza;
        do {
            plaza = parking.getPlazaLibre();
            if (plaza == -1) {
                Thread.yield();
            }
        } while ( plaza == -1 || !parking.aparcar(this, plaza));
        parking.mostrarPlazas();
    }*/

    @Override
    public void run() {
        parking.aparcar(this);
        try {
            Thread.sleep(rnd.nextInt(100,500));
        } catch (InterruptedException ex) {
            System.out.println("Error mientras hacía recados");
        }

        parking.salir(this);
    }
}