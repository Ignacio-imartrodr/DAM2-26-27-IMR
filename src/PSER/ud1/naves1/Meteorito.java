package PSER.ud1.naves1;

public class Meteorito {
    private String nombre;
    private NaveA naveA;
    private boolean hole;
    private boolean hasBS;

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

    public synchronized boolean hasBS(){
        return hasBS;
    }

    public void setBS(boolean  hasBS) {
        this.hasBS = hasBS;
    }

    public String getNaveAName() {
        return hasNaveA() ? naveA.getName() : null;
    }
}
