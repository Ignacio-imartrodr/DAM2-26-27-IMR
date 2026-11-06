package PSER.ventas;

import java.util.Random;

public class Cliente extends Thread {
    private static Random rnd = new Random();
    private boolean verDetalle;
    private Concesionario conc;

    public Cliente(String name, Concesionario concesionario, boolean verDetalle) {
        super(name);
        this.conc = concesionario;
        this.verDetalle = verDetalle;
    }

    @Override
    public void run() {
        Coche c;
        boolean bought = false;
        do {
            c = conc.escogerCoche();
            if (c != null) {
                bought = valorarCoche(c);
                bought = (conc.compraCoche(c, bought) && bought);
            } else if (verDetalle) {
                hablar("No le quedaban coches que comprar");
            }
        } while (!(c == null || bought));
    }

    private boolean valorarCoche(Coche c){
        int res = (rnd.nextInt(100) + 1);
        int prov = c.getVisitas();
        if (verDetalle) {
            hablar("Provando coche " + c + "...");
        }
        try {
            Thread.sleep(rnd.nextLong(1000));
        } catch (InterruptedException e) {
            hablar("Error decidiendo sobre el coche");
        }
        if (res <= prov) {
            hablar("Compró " + c);
            return true;
        }
        if (verDetalle) {
            hablar("No le convence el coche " + c);
        }
        return false;
    }
    private void hablar(String txt) {
        System.out.println(getName() + " > " + txt);
    }
}
