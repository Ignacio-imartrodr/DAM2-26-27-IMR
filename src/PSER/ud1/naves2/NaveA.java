package PSER.ud1.naves2;

import java.util.Random;

public class NaveA extends Nave {
    private static final Random RND = new Random();

    protected NaveA(String name, Central central, boolean verDetalle) {
        super(name, central, verDetalle);
    }

    @Override
    public boolean validarMeteorito(Meteorito meteorito) {
        return !meteorito.hasHole()
                && meteorito.getNaveA() == null
                && !meteorito.hasBS();
    }

    @Override
    protected void tratarMeteorito(Meteorito meteorito) {
        boolean interrumpida = false;

        //Llega
        if (VER_DETALLE) {
            comunicar("Llegó al meteorito " + meteorito.getName());
        }

        //Hace el agujero
        try {
            Thread.sleep(RND.nextLong(1, 1000));
        } catch (InterruptedException e) {
            comunicar("Interrumpida mientras taladraba");
            interrumpida = true;
        }

        CENTRAL.completarPerforacion(meteorito, this);
        if (VER_DETALLE) {
            comunicar("Perforó el meteorito " + meteorito.getName());
        }

        //Espera por combustible e irse
        CENTRAL.esperarCombustibleYSalir(meteorito, this);
        if (VER_DETALLE) {
            comunicar("Dejó el meteorito " + meteorito.getName());
        }

        if (interrumpida) {
            interrupt();
        }
    }

    @Override
    void reservarMeteorito(Meteorito meteorito) {
        meteorito.setNaveA(this);
    }
}