package PSER.naves1;

import java.util.Random;

public class NaveA extends Nave {
    private final static Random RND = new Random(); 

    protected NaveA(String name, Central central, boolean verDetalle) {
        super(name, central, verDetalle);
    }

    @Override
    public boolean validarMeteorito(Meteorito meteorito) {
        return (!meteorito.hasHole() && !meteorito.hasNaveA() && !meteorito.hasBS());
    }

    @Override
    protected Runnable tratarMeteorito() {
        NaveA n = this;
        return new Runnable() {
            @Override
            public void run() {
                synchronized(meteorito){
                    //Llega
                    meteorito.setNaveA(n);
                    if (VER_DETALLE) {
                        comunicar("Llegó al meteorito " + meteorito.getName());
                    }

                    //Hace el agujero
                    try {
                        Thread.sleep(RND.nextLong(1, 1000));;
                    } catch (InterruptedException e) {
                        comunicar("Error taladrando");
                    }
                    meteorito.setAndGetHole(n);
                    if (VER_DETALLE) {
                        comunicar("Perforó el meteorito " + meteorito.getName());
                    }
                    
                    //Avisa de que terminó
                    synchronized(central){
                        central.notifyAll();
                    }

                    //Espera por combustible
                    while (!meteorito.hasBS()) {
                        try {
                            meteorito.wait();
                        } catch (InterruptedException e) {
                            comunicar("Error esperando por nave BS");
                        }
                    }

                    //Se va
                    meteorito.setNaveA(null);
                    meteorito.notify();
                    if (VER_DETALLE) {
                        comunicar("Dejó el meteorito " + meteorito.getName());
                    }
                }
            }
        };
    }

}
