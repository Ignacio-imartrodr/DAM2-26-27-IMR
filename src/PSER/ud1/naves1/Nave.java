package PSER.ud1.naves1;

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
        Meteorito m = central.getMeteorito(this);
        while (m != null) {
            if (validarMeteorito(m)) {
                meteorito = m;
                tratarMeteorito.run();
            }
            m = central.getMeteorito(this);
        }
    }

    protected abstract Runnable tratarMeteorito();
    public abstract boolean validarMeteorito(Meteorito meteorito);

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (getClass() == obj.getClass()) {
            return this.getName().equals(getClass().cast(obj).getName());
        }
        return false;
    }
    
    protected void comunicar(String msj){
        System.out.println(getName() + " > " + msj);
    }
}
