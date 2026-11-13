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

    public boolean hasHole(){
        return hole;
    }
    
    public boolean setAndGetHole(NaveA n) {
        if (n != null && n.equals(naveA)) {
            hole = true;
        }
        return hole;
    }

    public String getName(){
        return nombre;
    }

    public boolean hasNaveA(){
        return naveA != null;
    }
}
