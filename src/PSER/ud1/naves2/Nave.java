package PSER.ud1.naves2;

public abstract class Nave extends Thread {
    protected final Central CENTRAL;
    protected final boolean VER_DETALLE;

    protected Nave(String name, Central central, boolean verDetalle) {
        super(name);
        this.CENTRAL = central;
        this.VER_DETALLE = verDetalle;
    }

    @Override
    public void run() {
        try {
            Meteorito meteorito;
            while (!isInterrupted()
                    && (meteorito = CENTRAL.getMeteorito(this)) != null) {
                tratarMeteorito(meteorito);
            }
        } catch (InterruptedException e) {
            interrupt();
        }
    }

    public abstract boolean validarMeteorito(Meteorito meteorito);

    abstract void reservarMeteorito(Meteorito meteorito);

    protected abstract void tratarMeteorito(Meteorito meteorito);

    protected void comunicar(String msj) {
        System.out.println(getName() + " > " + msj);
    }

    protected void notificarReserva(){}
}