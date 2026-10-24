package PSER.aparcamiento;

/**
 * Plaza
 */
class Plaza {
    private Coche c;

    public Plaza() {
        this.c = null;
    }

    public synchronized boolean aparcar(Coche c) {
        if (isLibre()) {
            this.c = c;
            return true;
        }
        return false;
    }

    public synchronized boolean liberar(Coche c){
        boolean isLeaving = !isLibre() && this.c.equals(c);
        if (isLeaving) {
            this.c = null;
        }
        return isLeaving;
    }

    public synchronized boolean isLibre() {
        return c == null;
    }

    @Override
    public String toString() {
        return isLibre() ? "XX" : this.c.getName();
    }
    
}