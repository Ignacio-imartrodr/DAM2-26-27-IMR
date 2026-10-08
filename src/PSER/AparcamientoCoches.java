package PSER;

import java.util.Random;

public class AparcamientoCoches {
    final static int N_PLAZAS = 10;
    final static int N_COCHES = 50;
    public static void main(String[] args) {
        Parking parking = new Parking(N_PLAZAS);
        parking.mostrarPlazas();
        Coche[] coches = new Coche[N_COCHES];
        for (int i = 0; i < N_COCHES; i++) {;
            coches[i] = new Coche("C" + /*i*/String.valueOf((char)('A' + i)), parking);
            coches[i].start();
        }
    }

    /**
     * Coche
     */
    private static class Coche extends Thread {
        static Random rnd = new Random();
        private Parking parking;

        public Coche(String nombre, Parking parking) {
            super(nombre);
            this.parking = parking;
        }

        @Override
        public void run() {
            aparcar();

            try {
                Thread.sleep(rnd.nextInt(1000,5000));
            } catch (InterruptedException ex) {
                System.out.println("Error mientras esperaba");
            }

            parking.salir(this);
        }
        public void aparcar(){
            int plaza;
            do {
                plaza = parking.getPlazaLibre();
                if (plaza == -1) {
                    Thread.yield();
                }
            } while ( plaza == -1 || !parking.aparcar(this, plaza));
            parking.mostrarPlazas();
        }
    }

    /**
     * Plaza
     */
    private static class Plaza {
        private Coche c;

        public Plaza() {
            this.c = null;
        }

        public synchronized boolean aparcar(Coche c) {
            if (isLibre()) {
                this.c = c;
                return true;
            }
            return false;
        }

        public synchronized boolean liberar(Coche c){
            boolean isLeaving = !isLibre() && this.c.equals(c);
            if (isLeaving) {
                this.c = null;
            }
            return isLeaving;
        }

        public synchronized boolean isLibre() {
            return c == null;
        }

        @Override
        public String toString() {
            return isLibre() ? "XX" : this.c.getName();
        }
        
    }

    /**
     * Parking
     */
    private static class Parking {
        Plaza[] plazas;

        public Parking(int nPlazas) {
            this.plazas = new Plaza[nPlazas];
            for (int i = 0; i < this.plazas.length; i++) {
                this.plazas[i] = new Plaza();
            }
        }

        public synchronized void mostrarPlazas() {
            String parking = "";
            for (Plaza p : plazas) {
                parking += "|" + p;
            }
            parking += "|";
            System.out.println(parking);
        }

        public boolean aparcar(Coche c, int plaza) {
            return plazas[plaza].aparcar(c);
        }
        
        public boolean salir(Coche c){
            boolean isLeaving = false;
            for (Plaza plaza : plazas) {
                if (isLeaving = plaza.liberar(c)) {
                    break;
                }
            }
            if (isLeaving) {
                mostrarPlazas();
            }
            return isLeaving;
        }

        private int getPlazaLibre() {
            for (int i = 0; i < plazas.length; i++) {
                if (plazas[i].isLibre()) {
                    return i;
                }
            }
            return -1;
        }
    }
}
