package PSER.oficina;

import java.util.ArrayList;
import java.util.List;

public class Oficina {
    Jefe j;
    List<Personal> PersonalEnOficina;

    public Oficina() {
        this.j = null;
        PersonalEnOficina = new ArrayList<>();
    }

    public Personal[] getPersonalEnOficinal(){
        return PersonalEnOficina.toArray(new Personal[0]);
    }

    public synchronized boolean entrar(Personal p) {
        if (p == null) {
            return false;
        }
        return PersonalEnOficina.add(p);
    }

    public synchronized boolean salir(Personal p) {
        if (p == null) {
            return false;
        }
        return PersonalEnOficina.remove(p);
    }

    public synchronized boolean setJefe(Jefe jefe){
        if (jefe != null && this.j == null) {
            this.j = jefe;
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized boolean removeJefe(Jefe jefe){
        if (jefe != null && this.j != null) {
            if (j.equals(jefe)) {
                this.j = null;
                notifyAll();
                return true;
            }
        }
        return false;
    }

    public boolean hasJefe() {
        return j != null;
    }
}