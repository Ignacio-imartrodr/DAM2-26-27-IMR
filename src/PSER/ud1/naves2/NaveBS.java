package PSER.ud1.naves2;

public class NaveBS extends Nave {
    private String nombreNaveA;

    protected NaveBS(String name, Central central, boolean verDetalle) {
        super(name, central, verDetalle);
    }

    @Override
    public boolean validarMeteorito(Meteorito meteorito) {
        return meteorito.hasHole()
                && meteorito.getNaveA() != null
                && !meteorito.hasBS();
    }

    @Override
    protected void tratarMeteorito(Meteorito meteorito) {
        //Llega, rellena la nave y la hace marchar
        if (VER_DETALLE) {
            comunicar("Llegó al meteorito " + meteorito.getName());
            comunicar("Rellenó el tanque de la nave " + nombreNaveA);
        }
        
        //Se va tras dejar la bomba
        boolean exito = CENTRAL.esperarSalidaYDestruir(meteorito);
        if (VER_DETALLE) {
            comunicar("Se va del meteorito " + meteorito.getName());
        }
        comunicar(exito
                ? "Meteorito " + meteorito.getName() + " destruido"
                : "Meteorito " + meteorito.getName() + " resistió la explosión");
    }

    @Override
    void reservarMeteorito(Meteorito meteorito) {
        nombreNaveA = meteorito.getNaveA().getName();
        meteorito.setBS(true);
    }

    @Override
    protected void notificarReserva() {
        if (holdsLock(CENTRAL)) {
            CENTRAL.notifyAll();
        }
    }
}