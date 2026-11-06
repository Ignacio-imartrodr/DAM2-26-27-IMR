package PSER.ventas;

import java.util.ArrayList;
import java.util.List;

public class Concesionario {
    private List<Coche> coches;
    private boolean hayStock;

    public Concesionario() {
        this.coches = new ArrayList<>();
    }
    public boolean addCoche(String modelo, int cantidad){
        if (cantidad <= 0) {
            return false;
        }
        return coches.add(new Coche(modelo, cantidad));
    }

    public synchronized Coche escogerCoche() {
        hayStock = true;
        while (hayStock) {
            hayStock = false;
            for (Coche coche : coches) {
                if (coche.getCantidad() > 0) {
                    hayStock = true;
                    if (coche.startVisita()) {
                        return coche;
                    }
                }
            }
            if (hayStock) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Error esperando por un coche para ver");
                }
            }
        }
        notify();
        return null;
    }

    public boolean compraCoche(Coche coche, boolean buy) {
        int pos = -1;
        try {
            pos = coches.indexOf(coche);
        } catch (NullPointerException e) {
            //Un coche null no se puede comprar
            return false;
        }
        if (pos < 0) {
            return false;
        }
        Coche c = coches.get(pos);
        synchronized(c){
            if (buy) {
                if (c.getCantidad() > 0) {
                    c.addCantidad(-1);
                } else {
                    return false;
                }
            }
            c.endVisita();
            synchronized(this){
                notify();
            }
        }
        return true;
    }
}
