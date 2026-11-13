package PSER.naves;

public abstract class Nave extends Thread {
    Central central;
    Meteorito meteorito;

    public Nave(String name, Central central) {
        super(name);
        this.central = central;
    }
    
    @Override
    public void run() {
        comportamiento().run();
    }

    protected abstract Runnable comportamiento();
    protected abstract boolean validarMeteorito(Meteorito meteorito);

    protected Meteorito obtenerMeteoritoObjetivo() {
        //TODO terminar y corregir
        Meteorito m = central.getMeteorito();
        if (m != null && validarMeteorito(m)) {
            
        }
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
    
}
