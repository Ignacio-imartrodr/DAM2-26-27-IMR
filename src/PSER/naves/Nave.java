package PSER.naves;

public abstract class Nave extends Thread {
    
    private Runnable tratarMeteorito;
    protected Central central;
    protected Meteorito meteorito;
    protected final boolean VER_DETALLE;

    protected Nave(String name, Central central, boolean verDetalle) {
        super(name);
        this.central = central;
        VER_DETALLE = verDetalle;
        tratarMeteorito = tratarMeteorito();
    }
    
    @Override
    public void run() {
        Meteorito m = obtenerMeteoritObjetivo();
        while (m != null) {
            synchronized(m){
                meteorito = m;
                tratarMeteorito.run();
            }
            m = central.getMeteorito();
        }
    }

    protected abstract Runnable tratarMeteorito();
    protected abstract boolean validarMeteorito(Meteorito meteorito);

    protected Meteorito obtenerMeteoritObjetivo() {
        Meteorito m;
        do {
            m = central.getMeteorito();
        } while (m != null && !validarMeteorito(m));
        return m;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (getClass() == obj.getClass()) {
            return this.getName().endsWith(getClass().cast(obj).getName());
        }
        return false;
    }
    
    protected void comunicar(String msj){
        System.out.println(getName() + " > " + msj);
    }
}
