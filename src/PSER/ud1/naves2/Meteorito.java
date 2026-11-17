package PSER.ud1.naves2;

public class Meteorito {
    private final String NAME;
    private NaveA naveA;
    private boolean hole;
    private boolean hasBS;

    public Meteorito(String nombre) {
        this.NAME = nombre;
    }

    public String getName() {
        return NAME;
    }

    public NaveA getNaveA() {
        return naveA;
    }

    public void setNaveA(NaveA naveA) {
        this.naveA = naveA;
    }

    public boolean hasHole() {
        return hole;
    }

    public void setHole(boolean hole) {
        this.hole = hole;
    }

    public boolean hasBS() {
        return hasBS;
    }

    public void setBS(boolean hasBS) {
        this.hasBS = hasBS;
    }
}