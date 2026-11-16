package PSER.naves;

public class NaveBS extends Nave {


    protected NaveBS(String name, Central central, boolean verDetalle) {
        super(name, central, verDetalle);
    }

    @Override
    protected Runnable tratarMeteorito() {
        return new Runnable() {
            @Override
            public void run() {
                synchronized(meteorito){
                    //Llega, rellena la nave y la hace marchar
                    if (VER_DETALLE) {
                        comunicar("Llegó al meteorito " + meteorito.getName());
                        comunicar("Rellenó el tanque de la nave " + meteorito.getNaveAName());
                    }
                    meteorito.notify();
                    //Espera a que marche la nave A
                    try {
                        meteorito.wait();
                    } catch (InterruptedException e) {
                        comunicar("Error esperando a que nave " + meteorito.getNaveAName() + " marche");
                    }

                    //Se va tras dejar la bomba
                    boolean exito = central.destruirMeteorito(meteorito);
                    if (VER_DETALLE) {
                        comunicar("Se va del meteorito " + meteorito.getName());
                        if (exito) {
                            comunicar("Meteorito " + meteorito.getName() + " destruído");
                        } else {
                            comunicar("Meteorito " + meteorito.getName() + " resistió la explosión");
                        }
                    }
                }
            }
        };
    }

    @Override
    protected boolean validarMeteorito(Meteorito meteorito) {
        return (meteorito.hasHole() && meteorito.hasNaveA());
    }

}
