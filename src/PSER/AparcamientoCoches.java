package PSER;

import java.util.Random;

public class AparcamientoCoches {
    final static int N_PLAZAS = 10;
    final static int N_COCHES = 50;
    public static void main(String[] args) {
        Parking parking = new Parking(N_PLAZAS);
        parking.mostrarPlazas();
        Coche[] coches = new Coche[N_COCHES];
        for (int i = 0; i < N_COCHES; i++) {
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

        private boolean aparcar(Coche c, int plaza) {
            if (plaza < 0 || plaza >= plazas.length) {
                return false;
            }
            return plazas[plaza].aparcar(c);
        }

        public synchronized void aparcar(Coche c){
            int plaza;
            if ((plaza = getPlazaLibre()) == -1) {
                try {
                    wait(); //HACE QUE ESPEREN POR EL PARKING, ESPERA A QUE NOTIFIQUEN UNA LIBERACIÓN
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                plaza = getPlazaLibre();
            }
            aparcar(c, plaza);
            
         /* int plaza; // ES LO MISMO QUE ARRIVA PERO SE ASEGURA QUE LA VA A APARCAR SIEMPRE AUNQUE NO HAGA FALTA GRACIAS A USAR notify() EN VEZ DE notifyAll()
            while ((plaza = getPlazaLibre()) == -1) {
                try {
                    wait(); //HACE QUE ESPEREN POR EL PARKING, ESPERA A QUE NOTIFIQUEN UNA LIBERACIÓN
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            aparcar(c, plaza); */

          /*int plaza = getPlazaLibre();
            if (plaza == -1) {
                try {
                    c.wait(); // NO HACE ESPERAR AL COCHE HACE QUE ESPEREN POR ÉL
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                aparcar(c, getPlazaLibre());
            } else {
                aparcar(c, plaza); 
            }*/
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
                synchronized (this){
                    notify();
                }
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
