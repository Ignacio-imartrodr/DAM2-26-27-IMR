package PSER.naves;

public class Meteorito {
    private String nombre;
    private NaveA naveA;
    private boolean hole;

    public Meteorito(String nombre) {
        this.nombre = nombre;
        this.naveA = null;
        this.hole = false;
    }

    public synchronized boolean hasHole(){
        return hole;
    }
    
    public synchronized boolean setAndGetHole(NaveA n) {
        if (n != null && n.equals(naveA)) {
            hole = true;
        }
        return hole;
    }
    public String getName(){
        return nombre;
    }

    public synchronized boolean hasNaveA(){
        return naveA != null;
    }
    
    public void setNaveA(NaveA naveA) {
        this.naveA = naveA;
    }

    public String getNaveAName() {
        return hasNaveA() ? naveA.getName() : null;
    }
}
