package PSER.oficina;

public class Oficina {
    Jefe j;

    public Oficina() {
        this.j = null;
    }

    public Oficina(Jefe j) {
        this.j = j;
    }

    public synchronized void entrar(Jefe jefe) {
        this.j = jefe;
    }

    public boolean hasJefe() {
        return j != null;
    }
}