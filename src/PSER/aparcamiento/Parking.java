package PSER.aparcamiento;

/**
 * Parking
 */
public class Parking {
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
        while ((plaza = getPlazaLibre()) == -1) {
            try {
                wait(); //HACE QUE ESPEREN POR EL PARKING, ESPERA A QUE NOTIFIQUEN UNA LIBERACIÓN
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        aparcar(c, plaza);
        
        /*// ES LO MISMO QUE ARRIVA PERO NO SE ASEGURA QUE VA A APARCAR SIEMPRE, HACE FALTA POR SI ENTRA OTRO A LA VEZ QUE NOTIFICAN PLAZA AUNQUE USE notify() EN VEZ DE notifyAll()
        int plaza;
        if ((plaza = getPlazaLibre()) == -1) {
            try {
                wait(); //HACE QUE ESPEREN POR EL PARKING, ESPERA A QUE NOTIFIQUEN UNA LIBERACIÓN
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            plaza = getPlazaLibre();
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
            synchronized (this){
                mostrarPlazas();
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