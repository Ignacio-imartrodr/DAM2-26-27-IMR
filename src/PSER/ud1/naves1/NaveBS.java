package PSER.ud1.naves1;

public class NaveBS extends Nave {


    protected NaveBS(String name, Central central, boolean verDetalle) {
        super(name, central, verDetalle);
    }

    @Override
    public boolean validarMeteorito(Meteorito meteorito) {
        return (meteorito.hasHole() && meteorito.hasNaveA() && !meteorito.hasBS());
    }

    @Override
    protected Runnable tratarMeteorito() {
        return new Runnable() {
            @Override
            public void run() {
                synchronized(meteorito){
                    //Llega, rellena la nave y la hace marchar
                    meteorito.setBS(true);
                    if (VER_DETALLE) {
                        comunicar("Llegó al meteorito " + meteorito.getName());
                        comunicar("Rellenó el tanque de la nave " + meteorito.getNaveAName());
                    }
                    meteorito.notify();
                    
                    //Espera a que marche la nave A
                    while (meteorito.hasNaveA()) {
                        try {
                            meteorito.wait();
                        } catch (InterruptedException e) {
                            comunicar("Error esperando a que nave " + meteorito.getNaveAName() + " marche");
                        }
                    }

                    //Se va tras dejar la bomba
                    meteorito.setBS(false);
                    boolean exito = central.destruirMeteorito(meteorito);
                    if (VER_DETALLE) {
                        comunicar("Se va del meteorito " + meteorito.getName());
                    }
                    if (exito) {
                        comunicar("Meteorito " + meteorito.getName() + " destruído");
                    } else {
                        comunicar("Meteorito " + meteorito.getName() + " resistió la explosión");
                    }
                }
            }
        };
    }

}
